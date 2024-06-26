package database;

import models.order.Order;
import models.product.*;
import models.restaurant.Restaurant;
import services.audit.AuditDatabaseAction;
import services.audit.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductRepository extends BaseRepository {
    private static Connection connection = null;

    public static void initConnection() {
        printSuccess("ProductRepository: Connection initialized");
        connection = Connector.getConnection();
    }

    public static void addProduct(ProductInterface product) {
        AuditService.log("Product", AuditDatabaseAction.CREATE);

        try {
            Integer id = product.getId();
            String name = product.getName();
            Double price = product.getPrice();
            String type = null;

            if(product instanceof ProductItem) {
                type = "ITEM";

                PreparedStatement productStatement = connection.prepareStatement("""
                INSERT INTO Product (id, name, price, type)
                VALUES (?, ?, ?, ?)
                """);
                productStatement.setInt(1, id);
                productStatement.setString(2, name);
                productStatement.setDouble(3, price);
                productStatement.setString(4, type);

                try {
                    productStatement.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                        String warningMessage = "Product (id:" + product.getId() + ") already exists";
                        printWarning(warningMessage);
                    } else {
                        printException(e);
                    }
                }
            } else if(product instanceof SpecialProduct) {
                java.sql.Date availableUntil = null;
                type = "SPECIAL";
                Date availableUntilDate = ((SpecialProduct) product).getAvailableUntil();
                availableUntil = new java.sql.Date(availableUntilDate.getTime());

                PreparedStatement specialProductStatement = connection.prepareStatement("""
                INSERT INTO Product (id, name, price, available_until, type)
                VALUES (?, ?, ?, ?, ?)
                """);
                specialProductStatement.setInt(1, id);
                specialProductStatement.setString(2, name);
                specialProductStatement.setDouble(3, price);
                specialProductStatement.setDate(4, availableUntil);
                specialProductStatement.setString(5, type);

                try {
                    specialProductStatement.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                        String warningMessage = "Product (id:" + product.getId() + ") already exists";
                        printWarning(warningMessage);
                    } else {
                        printException(e);
                    }
                }
            } else {
                type = "MENU";
                String description = ((Menu) product).getDescription();
                Double discount = ((Menu) product).getDiscount();

                PreparedStatement menuStatementFinal = connection.prepareStatement("""
                INSERT INTO Product (id, name, price, description, discount, type)
                VALUES (?, ?, ?, ?, ?, ?)
                """);
                menuStatementFinal.setInt(1, id);
                menuStatementFinal.setString(2, name);
                menuStatementFinal.setDouble(3, price);
                menuStatementFinal.setString(4, description);
                menuStatementFinal.setDouble(5, discount);
                menuStatementFinal.setString(6, type);

                try {
                    menuStatementFinal.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                        String warningMessage = "Product (id:" + product.getId() + ") already exists";
                        printWarning(warningMessage);
                    } else {
                        printException(e);
                    }
                }

                PreparedStatement menuStatement = connection.prepareStatement("""
                INSERT INTO Product (id, name, price, available_until, type)
                VALUES (?, ?, ?, ?, ?)
                """);

                for(ProductItem item : ((Menu) product).getItems()) {
                    java.sql.Date availableUntilItem = null;
                    String typeItem = null;

                    if (item instanceof SpecialProduct) {
                        Date availableUntilItemDate = ((SpecialProduct) item).getAvailableUntil();
                        availableUntilItem = new java.sql.Date(availableUntilItemDate.getTime());
                        typeItem = "SPECIAL";
                    } else {
                        typeItem = "ITEM";
                    }

                    menuStatement.setInt(1, item.getId());
                    menuStatement.setString(2, item.getName());
                    menuStatement.setDouble(3, item.getPrice());
                    menuStatement.setDate(4, availableUntilItem);
                    menuStatement.setString(5, typeItem);
                    try {
                        menuStatement.executeUpdate();
                    } catch (SQLException e) {
                        if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                            String warningMessage = "Product (id:" + item.getId() + ") already exists";
                            printWarning(warningMessage);
                        } else {
                            printException(e);
                        }
                    }

                    PreparedStatement menuItemsStatement = connection.prepareStatement("""
                    INSERT INTO MenuItems (menu_id, product_item_id)
                    VALUES (?, ?)
                    """);
                    menuItemsStatement.setInt(1, product.getId());
                    menuItemsStatement.setInt(2, item.getId());
                    menuItemsStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Product (id:" + product.getId() + ") already exists";
                printWarning(warningMessage);
            } else {
                printException(e);
            }
        }
    }

    public static void deleteProduct(ProductInterface product) {
        AuditService.log("Product", AuditDatabaseAction.DELETE);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM Product
            WHERE id = ?
            """);
            preparedStatement.setInt(1, product.getId());

            preparedStatement.executeUpdate();

            if(product instanceof Menu) {
                PreparedStatement menuItemsStatement = connection.prepareStatement("""
                DELETE FROM MenuItems
                WHERE menu_id = ?
                """);
                menuItemsStatement.setInt(1, product.getId());
                menuItemsStatement.executeUpdate();

                for(ProductItem item : ((Menu) product).getItems()) {
                    PreparedStatement itemStatement = connection.prepareStatement("""
                    DELETE FROM Product
                    WHERE id = ?
                    """);
                    itemStatement.setInt(1, item.getId());
                    itemStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void updateProduct(ProductInterface product) {
        AuditService.log("Product", AuditDatabaseAction.UPDATE);

        try {
            Integer id = product.getId();
            String name = product.getName();
            Double price = product.getPrice();
            String type = null;

            if (product instanceof ProductItem) {
                type = "ITEM";

                PreparedStatement productStatement = connection.prepareStatement("""
                
                        UPDATE Product
                SET name = ?, price = ?
                WHERE id = ? AND type = ?
                """);
                productStatement.setString(1, name);
                productStatement.setDouble(2, price);
                productStatement.setInt(3, id);
                productStatement.setString(4, type);

                productStatement.executeUpdate();
            } else if(product instanceof SpecialProduct) {
                java.sql.Date availableUntil = null;
                type = "SPECIAL";
                Date availableUntilDate = ((SpecialProduct) product).getAvailableUntil();
                availableUntil = new java.sql.Date(availableUntilDate.getTime());

                PreparedStatement specialProductStatement = connection.prepareStatement("""
                UPDATE Product
                SET name = ?, price = ?, available_until = ?
                WHERE id = ? AND type = ?
                """);
                specialProductStatement.setString(1, name);
                specialProductStatement.setDouble(2, price);
                specialProductStatement.setDate(3, availableUntil);
                specialProductStatement.setInt(4, id);
                specialProductStatement.setString(5, type);

                specialProductStatement.executeUpdate();
            } else if(product instanceof Menu) {
                type = "MENU";
                String description = ((Menu) product).getDescription();
                Double discount = ((Menu) product).getDiscount();

                PreparedStatement menuStatementFinal = connection.prepareStatement("""
                UPDATE Product
                SET name = ?, price = ?, description = ?, discount = ?
                WHERE id = ? AND type = ?
                """);
                menuStatementFinal.setString(1, name);
                menuStatementFinal.setDouble(2, price);
                menuStatementFinal.setString(3, description);
                menuStatementFinal.setDouble(4, discount);
                menuStatementFinal.setInt(5, id);
                menuStatementFinal.setString(6, type);

                menuStatementFinal.executeUpdate();

                PreparedStatement menuItemsStatement = connection.prepareStatement("""
                DELETE FROM MenuItems
                WHERE menu_id = ?
                """);
                menuItemsStatement.setInt(1, product.getId());
                menuItemsStatement.executeUpdate();

                for(ProductItem item : ((Menu) product).getItems()) {
                    java.sql.Date availableUntilItem = null;
                    String typeItem = null;

                    if (item instanceof SpecialProduct) {
                        Date availableUntilItemDate = ((SpecialProduct) item).getAvailableUntil();
                        availableUntilItem = new java.sql.Date(availableUntilItemDate.getTime());
                        typeItem = "SPECIAL";
                    } else {
                        typeItem = "ITEM";
                    }

                    PreparedStatement menuStatement = connection.prepareStatement("""
                    UPDATE Product
                    SET name = ?, price = ?, available_until = ?
                    WHERE id = ? AND type = ?
                    """);

                    menuStatement.setString(1, item.getName());
                    menuStatement.setDouble(2, item.getPrice());
                    menuStatement.setDate(3, availableUntilItem);
                    menuStatement.setInt(4, item.getId());
                    menuStatement.setString(5, typeItem);
                    menuStatement.executeUpdate();

                    PreparedStatement menuItemsStatementFinal = connection.prepareStatement("""
                    INSERT INTO MenuItems (menu_id, product_item_id)
                    VALUES (?, ?)
                    """);
                    menuItemsStatementFinal.setInt(1, product.getId());
                    menuItemsStatementFinal.setInt(2, item.getId());
                    menuItemsStatementFinal.executeUpdate();
                }
            }
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static List<ProductInterface> getProductsForRestaurant(Integer resturantId) {
        AuditService.log("Product", AuditDatabaseAction.READ);

        List<ProductInterface> products = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT  p.id, p.name, p.price, p.description, p.type, p.available_until, p.discount
            FROM Product p, RestaurantProducts rp
            WHERE p.id = rp.product_id AND rp.restaurant_id = ?
            """);
            preparedStatement.setInt(1, resturantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                String type = resultSet.getString("type");

                switch(type) {
                    case "ITEM":
                        ProductInterface product = ProductFactory.createProductItem(id, name, price);
                        products.add(product);
                        break;
                    case "SPECIAL":
                        Date availableUntil = resultSet.getDate("available_until");
                        ProductInterface specialProduct = ProductFactory.createSpecialProduct(id, name, price, availableUntil);
                        products.add(specialProduct);
                        break;
                    case "MENU":
                        String description = resultSet.getString("description");
                        Double discount = resultSet.getDouble("discount");
                        List<ProductItem> menuProducts = new ArrayList<>();

                        PreparedStatement menuProductsStatement = connection.prepareStatement("""
                        SELECT  p.id, p.name, p.price, p.description, p.type, p.available_until
                        FROM Product p, MenuItems mp
                        WHERE p.id = mp.product_item_id AND mp.menu_id = ?
                        """);
                        menuProductsStatement.setInt(1, id);
                        ResultSet menuProductsResultSet = menuProductsStatement.executeQuery();

                        while (menuProductsResultSet.next()) {
                            Integer menuProductId = menuProductsResultSet.getInt("id");
                            String menuProductName = menuProductsResultSet.getString("name");
                            Double menuProductPrice = menuProductsResultSet.getDouble("price");
                            String menuProductType = menuProductsResultSet.getString("type");

                            switch(menuProductType) {
                                case "ITEM":
                                    ProductItem menuProduct = ProductFactory.createProductItem(menuProductId, menuProductName, menuProductPrice);
                                    menuProducts.add(menuProduct);
                                    break;
                                case "SPECIAL":
                                    Date menuProductAvailableUntil = menuProductsResultSet.getDate("available_until");
                                    ProductItem menuSpecialProduct = ProductFactory.createSpecialProduct(menuProductId, menuProductName, menuProductPrice, menuProductAvailableUntil);
                                    menuProducts.add(menuSpecialProduct);
                                    break;
                            }
                        }

                        ProductInterface menu = ProductFactory.createMenu(id, name, description,
                        menuProducts, discount);
                        products.add(menu);
                        break;
                }
            }
        } catch (SQLException e) {
            printException(e);
        }

        return products;
    }

    public static List<ProductInterface> getProductsForOrder(Integer orderId) {
        AuditService.log("Product", AuditDatabaseAction.READ);

        List<ProductInterface> products = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT  p.id, p.name, p.price, p.description, p.type, p.available_until, p.discount
            FROM Product p, OrderProducts op
            WHERE p.id = op.product_id AND op.order_id = ?
            """);
            preparedStatement.setInt(1, orderId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                String type = resultSet.getString("type");

                switch (type) {
                    case "ITEM":
                        ProductInterface product = ProductFactory.createProductItem(id, name, price);
                        products.add(product);
                        break;
                    case "SPECIAL":
                        Date availableUntil = resultSet.getDate("available_until");
                        ProductInterface specialProduct = ProductFactory.createSpecialProduct(id, name, price, availableUntil);
                        products.add(specialProduct);
                        break;
                    case "MENU":
                        String description = resultSet.getString("description");
                        Double discount = resultSet.getDouble("discount");
                        List<ProductItem> menuProducts = new ArrayList<>();

                        PreparedStatement menuProductsStatement = connection.prepareStatement(
                                """
                                                        
                                SELECT  p.id, p.name, p.price, p.description
                                      
                                                              available_until
                                                        FROM Product p, MenuItems mp
                                                        WHERE p.id = mp.
                                product_item_id AND mp.menu_id = ?
                        """);
                        menuProductsStatement.setInt(1, id);
                        ResultSet menuProductsResultSet = menuProductsStatement.executeQuery();

                        while (menuProductsResultSet.next()) {
                            Integer menuProductId = menuProductsResultSet.getInt("id");
                            String menuProductName = menuProductsResultSet.getString("name");
                            Double menuProductPrice = menuProductsResultSet.getDouble("price");
                            String menuProductType = menuProductsResultSet.getString("type");

                            switch (menuProductType) {
                                case "ITEM":
                                    ProductItem menuProduct = ProductFactory.createProductItem(menuProductId, menuProductName, menuProductPrice);
                                    menuProducts.add(menuProduct);
                                    break;
                                case "SPECIAL":
                                    Date menuProductAvailableUntil = menuProductsResultSet.getDate("available_until");
                                    ProductItem menuSpecialProduct = ProductFactory.createSpecialProduct(menuProductId, menuProductName, menuProductPrice, menuProductAvailableUntil);
                                    menuProducts.add(menuSpecialProduct);
                                    break;
                            }
                        }

                        ProductInterface menu = ProductFactory.createMenu(id, name, description, menuProducts, discount);
                        products.add(menu);
                        break;

                }
            }
        } catch (SQLException e) {
            printException(e);
        }

        return products;
    }

    public static ProductInterface getProductById(Integer id) {
        AuditService.log("Product", AuditDatabaseAction.READ);

        ProductInterface product = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT  p.id, p.name, p.price, p.description, p.type, p.available_until, p.discount
            FROM Product p
            WHERE p.id = ?
            """);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                String type = resultSet.getString("type");

                switch (type) {
                    case "ITEM":
                        product = ProductFactory.createProductItem(id, name, price);
                        break;
                    case "SPECIAL":
                        Date availableUntil = resultSet.getDate("available_until");
                        product = ProductFactory.createSpecialProduct(id, name, price, availableUntil);
                        break;
                    case "MENU":
                        String description = resultSet.getString("description");
                        Double discount = resultSet.getDouble("discount");
                        List<ProductItem> menuProducts = new ArrayList<>();

                        PreparedStatement menuProductsStatement = connection.prepareStatement("""
                        SELECT  p.id, p.name, p.price, p.description, p.type, p.available_until
                        FROM Product p, MenuItems mp
                        WHERE p.id = mp.product_item_id AND mp.menu_id = ?
                        """);
                        menuProductsStatement.setInt(1, id);
                        ResultSet menuProductsResultSet = menuProductsStatement.executeQuery();

                        while (menuProductsResultSet.next()) {
                            Integer menuProductId = menuProductsResultSet.getInt("id");
                            String menuProductName = menuProductsResultSet.getString("name");
                            Double menuProductPrice = menuProductsResultSet.getDouble("price");
                            String menuProductType = menuProductsResultSet.getString("type");

                            switch (menuProductType) {
                                case "ITEM":
                                    ProductItem menuProduct = ProductFactory.createProductItem(menuProductId, menuProductName, menuProductPrice);
                                    menuProducts.add(menuProduct);
                                    break;
                                case "SPECIAL":
                                    Date menuProductAvailableUntil = menuProductsResultSet.getDate("available_until");
                                    ProductItem menuSpecialProduct = ProductFactory.createSpecialProduct(menuProductId, menuProductName, menuProductPrice, menuProductAvailableUntil);
                                    menuProducts.add(menuSpecialProduct);
                                    break;
                            }
                        }

                        product = ProductFactory.createMenu(id, name, description, menuProducts, discount);
                        break;
                }
            }
        } catch (SQLException e) {
            printException(e);
        }

        return product;
    }
}

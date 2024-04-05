<h1>Food delivery app</h1>
<h2>A Java project meant to illustrate oop concepts</h2>
<img src='https://github.com/w-i-l/avl-tree/assets/65015373/5bc87bc6-41e8-4203-9d68-7e0e4191d49f'>


<br>
<hr>
<h2>About it</h2>
<p>This app copy the behavior from Bolt Food allowing user to view restaurants, their products and place an order.</p>
<p>The interaction is made via console, providing two custom menus, one for <code>admin</code> and one for regular <code>user</code>.</p>


<br>
<hr>
<h2>How to use it</h2>
<p>All the data are hardcoded and found in <a href="https://github.com/w-i-l/food-delivery-app/tree/main/src/mockedData">mocked data</a> folder. They are made to provide a minimum usability to this project.</a></p>
<p>There is no need for any external dependencies.</p>
<p>After running the app, you will be prompted to choose between <code>admin</code> and <code>user</code> menu.</p>
<p>For choosing <code>admin</code> menu you will be asked to enter the password which is <code><b>admin</b></code>.</p>
<p>Below are illustrated the accesible commands for each user type:</p>
<ul>
    <li>Admin menu:
        <ul>
            <li>
                <p>Restaurants utils</p>
                <ul>
                    <li>View all restaurants</li>
                    <li>Add a restaurant</li>
                    <li>Remove a restaurant</li>
                    <li>Update a restaurant</li>
                    <li>Show restaurant details</li>
                </ul>
            </li>
            <li>
                <p>Driver utils</p>
                <ul>
                    <li>View all drivers</li>
                    <li>Add a driver</li>
                    <li>Remove a driver</li>
                    <li>Update a driver</li>
                    <li>Show driver details</li>
                </ul>
            </li>
            <li>
                <p>Customer utils</p>
                <ul>
                    <li>View all customers</li>
                    <li>Add a customer</li>
                    <li>Remove a customer</li>
                    <li>Update a customer</li>
                    <li>Show customer details</li>
                </ul>
            </li>
            <li>
                <p>Order utils</p>
                <ul>
                    <li>View all orders</li>
                    <li>Show order details</li>
                </ul>
            </li>
        </ul>
    </li>
    <li>User menu:
        <ul>
            <li>View all restaurants</li>
            <li>View restaurant menu</li>
            <li>Create order</li>
            <li>List all orders</li>
            <li>See order details</li>
            <li>Place order</li>
            <li>See order status</li>
        </ul>
    </li>
</ul>
<p>Both menus shares the same data resources, so every action in one will be seen in the other.</p>


<br>
<hr>
<h2>How it works</h2>
<p>They are 4 services that handle all the CRUD operations on each type of data:</p>
<ul>
    <li>Restaurant service</li>
    <li>Driver service</li>
    <li>Customer service</li>
    <li>Order service</li>
</ul>
<p>Each service provides functionalities only on the specific type on data. It also keeps a <code>set</code> with all the object available.</p>

<p>For the CLI, a <code>Menu Service</code> is used, which stores an instance of <code>User</code> and <code>Admin</code> services. In each service there are injected all 4 data services described above.</p>
<p>The source data are passed in the <code>main</code> function, being initialized with all the mocked data.</p>

<br>
<hr>
<h2>Tech specs</h2>
<p>Each model class has in its own package a <code>factory</code> class that generate an instance based on a static auto-incremental index. It provides two options for initialisation: one being with the arguments passed in costructor, the other being with an instance of <code>Scanner</code>, via command line.</p>

<h3>Address</h3>
<p>Attributes</p>
<ul>
    <li><code>longitude</code>: Double</li>
    <li><code>latitude</code>: Double</li>
    <li><code>name</code>: String</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>Double distanceTo(Address address)</code> - returns the distance between two addresses</li>
</ul>

<h3>Customer</h3>
<p>Attributes</p>
<ul>
    <li><code>id</code>: Integer</li>
    <li><code>name</code>: String</li>
    <li><code>address</code>: Address</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>void showCustomerDetails()</code> - prints the customer details</li>
    <li><code>void updateCustomer(Customer customer)</code> - updates the customer details, keeping the <code>id</code></li>
</ul>

<h3>Driver Type</h3>
<p>Enum values</p>
<ul>
    <li><code>BIKE</code></li>
    <li><code>CAR</code></li>
    <li><code>PEDESTRIAN</code></li>
</ul>

<p>Methods</p>
<ul>
    <li><code>Double getAverageSpeed()</code> - returns the average speed for each type</li>
    <li><code>String getName()</code> - returns the name of the type</li>
</ul>

<h3>Driver</h3>
<p>Attributes</p>
<ul>
    <li><code>id</code>: Integer</li>
    <li><code>name</code>: String</li>
    <li><code>type</code>: DriverType</li>
    <li><code>rating</code>: Integer</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>void showDriverDetails()</code> - prints the driver details</li>
    <li><code>Double getEstimatedDeliveryTime(Address from, Address to)</code> - returns the estimated delivery time between two addresses</li>
    <li><code>void updateDriver(Driver driver)</code> - updates the driver details, keeping the <code>id</code></li>
</ul>

<h3>Product Interface</h3>
<p>Methods</p>
<ul>
    <li><code>Integer getId()</code></li>
    <li><code>String getName()</code></li>
    <li><code>Double getPrice()</code></li>
    <li><code>void setName(String name)</code></li>
    <li><code>void setPrice(Double price)</code></li>
    <li><code>void showProductDetails()</code></li>
</ul>

<h3>Product Item: ProductInterface</h3>
<p>Attributes</p>
<ul>
    <li><code>id</code>: Integer</li>
    <li><code>name</code>: String</li>
    <li><code>price</code>: Double</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>void showProductDetails()</code> - prints the product details</li>
</ul>

<h3>Special Product: ProductInterface</h3>
<p>Attributes</p>
<ul>
    <li><code>availableUntil</code>: Date</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
</ul>

<h3>Menu: ProductInterface</h3>
<p>Attributes</p>
<ul>
    <li><code>id</code>: Integer</li>
    <li><code>name</code>: String</li>
    <li><code>originalPrice</code>: Double</li>
    <li><code>discountedPrice</code>: Double</li>
    <li><code>description</code>: String</li>
    <li><code>items</code>: List of ProductItem</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>void showMenuDetails()</code> - prints the menu details</li>
</ul>

<h3>Restaurant</h3>
<p>Attributes</p>
<ul>
    <li><code>id</code>: Integer</li>
    <li><code>name</code>: String</li>
    <li><code>address</code>: Address</li>
    <li><code>products</code>: List of ProductInterface</li>
</ul>

<p>Methods</p>
<ul>
    <li><code>getters and setter</code> - for attributes</li>
    <li><code>void showRestaurantDetails()</code> - prints the restaurant details</li>
    <li><code>void showMenu()</code> - prints the menu of the restaurant</li>
    <li><code>void updateRestaurant(Restaurant restaurant)</code> - updates the restaurant details, keeping the <code>id</code></li>
</ul>
<h1>Food delivery app</h1>
<h2>A console line interface JBDC project</h2>
<img src='https://github.com/w-i-l/avl-tree/assets/65015373/5bc87bc6-41e8-4203-9d68-7e0e4191d49f'>


<br>
<hr>
<h2>About it</h2>
<p>This app copy the behavior from Bolt Food allowing user to view restaurants, their products and place an order.</p>
<p>The interaction is made via console, providing two custom menus, one for <code>admin</code> and one for regular <code>user</code>.</p>

<p>For a brief JBDC tutorial, but also minimal setup of the project i recommend wathcing <a href="https://www.youtube.com/watch?v=KgXq2UBNEhA&ab_channel=MarcoCodes">this</a>.</p>


<br>
<hr>
<h2>How to use it</h2>

>**Note**: All the data are stored in a database which is not included in this bundle.</p>
<p>For developing this app I have used a <a href="https://www.mysql.com/">MySQL</a> database hosted in a docker container (<a href="https://hub.docker.com/_/mysql">docker image</a>) - a nice tutorial <a href="https://www.youtube.com/watch?v=kphq2TsVRIs&t=330s&ab_channel=DatabaseStar">here</a>. You will also need the <a href="https://vladmihalcea.com/jdbc-driver-maven-dependency/">connector driver</a>. After installing the connector add the connector make sure to add it as a dependecy for the project.</p>
<details>
<summary>How to add the connector as dependency (<a href="https://www.jetbrains.com/idea/">IntelliJ IDEA</a>)</summary>
<ol>
    <li>Click <code>File</code> from menu</li>
    <li>Click <code>Project Structure...</code></li>
    <li>In <code>Project Setting</code> click <code>Modules</code></li>
    <li>Under the <code>Module SDK</code> click <code>Add</code></li>
    <li>Select <code>JARs or directories...</code></li>
    <li>Navigate and finds the location for the downloaded connector driver.</li>
</ol>
</details>
<br/>

<p>After configuring the database change the credentials from <a href="https://github.com/w-i-l/food-delivery-app/blob/main/src/database/Connector.java">Connector.java</a> file to match yours.</p>

<p><b>You are all set!</b> Just run the <code>Main.java</code> file and everything should be fine.</p>

<p>Below are illustrated the accesible commands for each user type:</p>
<ul>
    <li>Admin menu:
        <ul>
            <li>
                <details>
                <summary>Restaurants utils</summary>
                <ul>
                    <li>View all restaurants</li>
                    <li>Add a restaurant</li>
                    <li>Remove a restaurant</li>
                    <li>Update a restaurant</li>
                    <li>Show restaurant details</li>
                </ul>
                </details>
            </li>
            <li>
                <details>
                    <summary>Driver utils</summary>
                    <ul>
                        <li>View all drivers</li>
                        <li>Add a driver</li>
                        <li>Remove a driver</li>
                        <li>Update a driver</li>
                        <li>Show driver details</li>
                    </ul>
                 </details>
            </li>
            <li>
                <details>
                    <summary>Customer utils</summary>
                    <ul>
                        <li>View all customers</li>
                        <li>Add a customer</li>
                        <li>Remove a customer</li>
                        <li>Update a customer</li>
                        <li>Show customer details</li>
                    </ul>
                </details>
            </li>
            <li>
                <details>
                    <summary>Order utils</summary>
                    <ul>
                        <li>View all orders</li>
                        <li>Show order details</li>
                    </ul>
                </details>
            </li>
        </ul>
    </li>
    <li>
        <details>
            <summary>User menu</summary>
            <ul>
                <li>View all restaurants</li>
                <li>View restaurant menu</li>
                <li>Create order</li>
                <li>List all orders</li>
                <li>See order details</li>
                <li>Place order</li>
                <li>See order status</li>
            </ul>
        </details>
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

<p>For each model provided in the <code>model</code> folder exists a repository which manage all the CRUD operations specific for that model and log any operations perform on database (see <a href="https://github.com/w-i-l/food-delivery-app/blob/main/src/services/audit/AuditService.java">audit service</a> for more details).</p>

<p>For the CLI, a <code>Menu Service</code> is used, which stores an instance of <code>User</code> and <code>Admin</code> services. In each service there are injected all 4 data services described above.</p>

<p>The source data are passed in the <code>main</code> function, being initialized from mocked data from the <code>/database/sql/database_insertion.sql</code> file.</p>

<br>
<hr>
<h2>Tech specs</h2>
<figure>
    <img id="uml_diagram" src="https://github.com/w-i-l/food-delivery-app/assets/65015373/1b521fdf-815f-44f3-b6a9-e14688e11c85"/>
    <br/>
    <figcaption for="uml_diagram">UML diagram</figcaption>
</figure>

<p>Each model class has in its own package a <code>factory</code> class that generate an instance based on a static auto-incremental index. It provides two options for initialisation: one being with the arguments passed in costructor, the other being with an instance of <code>Scanner</code>, via command line.</p>

<p>After the connection is enabled, database will be generated from the <code>/database/sql/database_creation.sql</code> file and populate the database if all tables are empty which will prevent duplicates. The tables constains foreign keys which are set to be <code>ON DELETE CASCADE</code>.</p>

<p>After the initialisation of the schema, the factories's static counters are initialized with the <code>maximum id + 1</code> from the corresponding table which prevents having duplicated primary keys.</p>

<p>Services and database each store a copy of a record, the synchronization happens at the start of the program and while it is running.</p>

<details>
    <summary>Documentation</summary></h2>
    <h3>Address</h3>
    <p>Attributes</p>
    <ul>
        <li><code>longitude</code>: Double</li>
        <li><code>latitude</code>: Double</li>
        <li><code>name</code>: String</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>Double distanceTo(Address address)</code> - returns the distance between two addresses</li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Customer</h3>
    <p>Attributes</p>
    <ul>
        <li><code>id</code>: Integer</li>
        <li><code>name</code>: String</li>
        <li><code>address</code>: Address</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>void showCustomerDetails()</code> - prints the customer details</li>
        <li><code>void updateCustomer(Customer customer)</code> - updates the customer details, keeping the <code>id</code></li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Driver Type</h3>
    <p>Enum values</p>
    <ul>
        <li><code>BIKE</code></li>
        <li><code>CAR</code></li>
        <li><code>PEDESTRIAN</code></li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>Double getAverageSpeed()</code> - returns the average speed for each type</li>
        <li><code>String getName()</code> - returns the name of the type</li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Driver</h3>
    <p>Attributes</p>
    <ul>
        <li><code>id</code>: Integer</li>
        <li><code>name</code>: String</li>
        <li><code>type</code>: DriverType</li>
        <li><code>rating</code>: Integer</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>void showDriverDetails()</code> - prints the driver details</li>
        <li><code>Double getEstimatedDeliveryTime(Address from, Address to)</code> - returns the estimated delivery time between two addresses</li>
        <li><code>void updateDriver(Driver driver)</code> - updates the driver details, keeping the <code>id</code></li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
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
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Product Item: ProductInterface</h3>
    <p>Attributes</p>
    <ul>
        <li><code>id</code>: Integer</li>
        <li><code>name</code>: String</li>
        <li><code>price</code>: Double</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>void showProductDetails()</code> - prints the product details</li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Special Product: ProductInterface</h3>
    <p>Attributes</p>
    <ul>
        <li><code>availableUntil</code>: Date</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
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
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>void showMenuDetails()</code> - prints the menu details</li>
    </ul>
    <hr/>
    <br/>
    <!-- ------------ -->
    <h3>Restaurant</h3>
    <p>Attributes</p>
    <ul>
        <li><code>id</code>: Integer</li>
        <li><code>name</code>: String</li>
        <li><code>address</code>: Address</li>
        <li><code>products</code>: List of ProductInterface</li>
    </ul>
    <!-- ------------ -->
    <p>Methods</p>
    <ul>
        <li><code>getters and setter</code> - for attributes</li>
        <li><code>void showRestaurantDetails()</code> - prints the restaurant details</li>
        <li><code>void showMenu()</code> - prints the menu of the restaurant</li>
        <li><code>void updateRestaurant(Restaurant restaurant)</code> - updates the restaurant details, keeping the <code>id</code></li>
    </ul>
</details>

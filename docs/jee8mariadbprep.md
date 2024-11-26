#### **4. How to Prepare a Relational Database (MariaDB)**

### **Adding Windows Configuration and Setup for MariaDB**

#### **Step-by-Step Guide for Installing MariaDB on Windows 10**

---

#### **1. Download the Latest Stable MariaDB Release**
- Visit the [MariaDB All Releases](https://mariadb.org/mariadb/all-releases/) page to find the latest stable release.
- Navigate to [MariaDB Downloads](https://downloads.mariadb.org/mariadb/+releases/) to select the correct version for your system.
  - Ensure you select:
    - **Operating System**: Windows 10
    - **Architecture**: x86_64
    - **Package Type**: MSI Installer
- Example: **MariaDB 10.6.5** (latest stable release as of writing).

---

#### **2. Download the MSI Installer**
- Go to the [MariaDB Download Page](https://mariadb.org/download/), select the version, and download the `.msi` installer file.
- Example:
  - [MariaDB MSI Installer](https://mariadb.org/download/?t=mariadb&p=mariadb&r=10.6.5&os=windows&cpu=x86_64&pkg=msi&m=acorn).

---

#### **3. Run the MSI Installer**
1. Locate the downloaded `.msi` file in Windows Explorer.
2. Double-click the file to begin the installation process.
3. **Follow the Installation Wizard**:
   - Choose the installation directory (default: `C:\Program Files\MariaDB 10.x`).
   - Set up a root password when prompted (important for database security).
   - Optionally enable the MariaDB service to start automatically on system boot.
   - Select the features to install (default selections are usually sufficient).

---

#### **4. Verify the Installation via Command Line**
After installation, confirm that MariaDB is correctly installed and running:
1. Open the Command Prompt (`cmd`) as Administrator.
2. Verify MariaDB service status:
   ```cmd
   sc query mysql
   ```
   - If MariaDB is running, you will see "RUNNING" under `STATE`.

3. Log in to MariaDB:
   ```cmd
   mysql -u root -p
   ```
   - Enter the root password you set during installation.
   - You should now see the MariaDB command prompt.

4. Check the installed version:
   ```sql
   SELECT VERSION();
   ```
5. Create a test database:
   ```sql
   CREATE DATABASE test_db;
   USE test_db;
   SHOW TABLES;
   ```

---

#### **5. Additional Configuration**
If necessary, update configuration files for custom settings:
- Locate the `my.ini` or `my.cnf` file in the installation directory.
- Example default location: `C:\Program Files\MariaDB 10.x\data\my.ini`.

Add or modify configurations as needed:
```ini
[mysqld]
port=3306
bind-address=127.0.0.1
```

---

#### **6. Troubleshooting**
- If the MariaDB service doesn’t start:
  - Ensure the `mysqld` process isn’t blocked by antivirus or firewall.
  - Check logs in the `data` directory (`C:\Program Files\MariaDB 10.x\data\`).

---

This additional setup steps are for Linux and other Unix based systems

- **Step 1: Install MariaDB**:
  - Use a package manager or installer suitable for your operating system.
  - Example for Linux: 
    ```bash
    sudo apt update
    sudo apt install mariadb-server
    ```
- **Step 2: Configure MariaDB**:
  - Start the MariaDB service:
    ```bash
    sudo service mariadb start
    ```
  - Secure installation:
    ```bash
    sudo mysql_secure_installation
    ```
- **Step 3: Create a Database**:
  - Access the MariaDB client:
    ```bash
    mysql -u root -p
    ```
  - Create a new database:
    ```sql
    CREATE DATABASE sample_db;
    ```
  - Create a user and grant privileges:
    ```sql
    CREATE USER 'sample_user'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON sample_db.* TO 'sample_user'@'localhost';
    FLUSH PRIVILEGES;
    ```
- **Step 4: Verify Connection**:
  - Use a database client (e.g., MySQL Workbench, DBeaver) to connect to the MariaDB instance.


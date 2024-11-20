AUTOMAKE_OPTIONS = foreign

# Variables
BACKEND_DIR = backend
FRONTEND_DIR = frontend
CURRENT_MAVEN_PROFILE = dev

# Maven profiles for different modes
MAVEN_DEV_PROFILE = dev
MAVEN_TEST_PROFILE = test
MAVEN_PROD_PROFILE = prod

# NPM scripts for different modes
NPM_DEV_SCRIPT = start
NPM_TEST_SCRIPT = test
NPM_BUILD_SCRIPT = build

# DB credentials for the unittest
PG_PATH = /Library/PostgreSQL/12/bin
PG_DATA_DIR = /tmp/mytodoapp_psql_data
PG_PORT = 5433
PG_USER = test_user
PG_PASSWORD = test_password
PG_DB = test_todoapp

export PG_PATH
export PG_PORT
export PG_USER
export PG_PASSWORD
export PG_DB

# === PSQL Commands ===

init_db:
	@if [ ! -f $(PG_DATA_DIR)/PG_VERSION ]; then \
		$(PG_PATH)/initdb -D $(PG_DATA_DIR); \
		echo "port = $(PG_PORT)" >> $(PG_DATA_DIR)/postgresql.conf; \
		echo "unix_socket_directories = '$(PG_DATA_DIR)'" >> $(PG_DATA_DIR)/postgresql.conf; \
		echo "Database cluster initialized."; \
	else \
		echo "Database cluster already initialized."; \
	fi

start_psql: init_db
	@echo "Starting PostgreSQL on port $(PG_PORT)..."
	$(PG_PATH)/pg_ctl -w -D $(PG_DATA_DIR) -l $(PG_DATA_DIR)/default.log start
	$(PG_PATH)/createuser --createdb -h $(PG_DATA_DIR) -p $(PG_PORT) $(PG_USER) --superuser
	$(PG_PATH)/createdb -h $(PG_DATA_DIR) -p $(PG_PORT) -U $(PG_USER) $(PG_DB)
	$(PG_PATH)/psql -h $(PG_DATA_DIR) -p $(PG_PORT) -d $(PG_DB) -U $(PG_USER) -c "ALTER USER $(PG_USER) WITH PASSWORD '$(PG_PASSWORD)';"
	@sleep 3  # Give PostgreSQL time to start

stop_psql:
	@echo "Stopping PostgreSQL..."
	$(PG_PATH)/pg_ctl -D $(PG_DATA_DIR) stop
	rm -rf $(PG_DATA_DIR)

# === Build Commands ===

build_dev:
	@echo "Building backend and frontend for development..."
	cd $(BACKEND_DIR) && mvn clean package -P$(MAVEN_DEV_PROFILE) -Dmaven.test.skip=true
	cd $(FRONTEND_DIR) && npm install && npm run $(NPM_BUILD_SCRIPT)

build_prod:
	@echo "Building backend and frontend for production..."
	cd $(BACKEND_DIR) && mvn clean package -P$(MAVEN_PROD_PROFILE) -Dmaven.test.skip=true
	cd $(FRONTEND_DIR) && npm install && npm run $(NPM_BUILD_SCRIPT)

# === Start Commands ===

start_dev:
	@echo "Starting backend and frontend in development mode..."
	cd $(BACKEND_DIR) && mvn spring-boot:run -P$(MAVEN_DEV_PROFILE) &
	cd $(FRONTEND_DIR) && npm run $(NPM_DEV_SCRIPT)

start_prod:
	@echo "Starting backend and frontend in production mode..."
	cd $(BACKEND_DIR) && mvn spring-boot:run -P$(MAVEN_PROD_PROFILE) &
	cd $(FRONTEND_DIR) && npm run $(NPM_DEV_SCRIPT)

# === Test Commands ===

do_run_tests:
	cd $(BACKEND_DIR) && mvn test -P$(MAVEN_TEST_PROFILE)
	cd $(FRONTEND_DIR) && npm run $(NPM_TEST_SCRIPT)

run_tests:
	@echo "Running backend and frontend tests in testing mode..."
	trap '$(MAKE) stop_psql' EXIT
	trap '$(MAKE) stop_psql' ERR
	$(MAKE) start_psql
	if ! $(MAKE) do_run_tests; then \
		echo "Unit tests failed."; \
		exit 1; \
	fi

# Backend build and package
backend_build:
	@echo "Building backend..."
	cd backend && mvn clean package -DskipTests -P$(CURRENT_MAVEN_PROFILE)

# Frontend build and package
frontend_build:
	@echo "Building frontend..."
	cd frontend && npm install && npm run build

# Package the whole application
distdir_local = my-todo-app
dist: backend_build frontend_build
	@echo "Packaging the application..."
	mkdir -p $(distdir_local)
	cp -r backend/target/my-todo-app.jar $(distdir_local)/
	cp -r frontend/build $(distdir_local)/frontend

install-exec-local: dist
	@echo "Installing the application on Amazon Linux..."
	sudo mkdir -p /opt/$(distdir_local)
	sudo cp -r $(distdir_local)/* /opt/$(distdir_local)/

	@echo "Creating systemd service for My TODO App..."
	echo "[Unit]
	Description=My TODO App Backend Service
	After=network.target

	[Service]
	User=ec2-user
	WorkingDirectory=/opt/$(distdir_local)
	ExecStart=/usr/bin/java -jar /opt/$(distdir_local)/my-todo-app.jar
	Restart=on-failure

	[Install]
	WantedBy=multi-user.target" | sudo tee /etc/systemd/system/my-todo-app.service > /dev/null

	sudo systemctl daemon-reload
	sudo systemctl enable my-todo-app.service
	sudo systemctl start my-todo-app.service

clean-local:
	@echo "Cleaning project..."
	cd backend && mvn clean
	cd frontend && rm -rf node_modules build
	rm -rf $(distdir_local)


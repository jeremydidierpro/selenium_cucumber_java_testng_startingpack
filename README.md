# Selenium Java Cucumber Starting Pack


This project is a lightweight starting pack for building a maintainable and scalable Selenium automation framework using:


- Java
- Selenium WebDriver
- Cucumber (BDD)
- Maven
- RestAssured
- Extend reports


It was created as a reference framework to illustrate how I usually structure automation projects in a real professional environment.


---


## Project Goals


The main objectives of this framework are:


- Provide a clean **layered architecture**
- Improve test robustness through **WebDriver wrappers**
- Isolate responsibilities to keep the framework **easy to maintain and extend**
- Demonstrate good practices for UI, API, and data-driven testing


---


## Architecture Overview


The framework is organized in clear layers:


- **Driver & Waiter Wrappers**
  Wrap all WebDriver interactions to add retry logic, safety, and logging.


- **Page Object Model (POM)**
  Encapsulates business logic and uses only the wrapper layer (never raw WebDriver).


- **Step Definitions**
  Uses only the POM layer.


- **Gherkin Features**
  Describe test scenarios in business-readable language.


Additional modules are separated by responsibility:
- Reporting
- Test data management
- Utilities
- API and SQL adapters


---


## How to Run the Tests


1. Copy `config_sample.json` and rename it to `config.json`
2. Adjust configuration values if needed
3. Run:


```bash
mvn clean test

or with optional parameters:

mvn clean test  -DENVIRONMENT=qa -DBROWSER=chrome -DREMOTE=false -DHOST=localhost -DPORT=4444 -DRETRY=1 -DdpThreads=2 -Dcucumber.filter.tags=@SMOKE_TEST

```

## GitHub Actions

This project can also be executed through **GitHub Actions**.

- Workflow location: `.github/workflows/CI.yml`
- Run it from the **Actions** tab (manual trigger)
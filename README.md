# Running the CRM API Locally

You have **two ways** to start the CRM API and its database:

---

## Option 1: Manual Steps

1. **Build the JAR**
```bash
./gradlew clean build
```

2. **Build the Docker image**
```bash
docker compose build crm-api
```

3. **Start Docker Compose**

docker compose up

4. **Access the application at:**

http://localhost:8080

Logs will be streamed directly in your terminal.
## Option 2: One-Command Setup

Use the provided script to automate all steps:

```
./local-setup.sh
```

This will:

    Stop and remove any existing containers

    Build the Gradle JAR

    Build the Docker image

    Start Docker Compose and attach logs in the terminal

Next, populate the database with random entries:

```
./seeding.sh
```

Access the application at:

http://localhost:8080


5. **Performing Queries**

The API has a custom query builder to try find opportunities given certain parameters.

To check opportunities simply make a POST call to /query:

```
/api/oppotunities/query
```

And send as body parameter at least one of the following attributes:

```
{
    "type": "VISIT",
    "customerId": 2,
    "timestampStart": "2025-10-20T00:00:00Z",
    "timestampEnd": "2025-10-23T23:59:59Z",
    "value": "Product 16248"

}

```

You can replace your query as you wish following the previous example.

6. **Main Idea**
   The custom query builder should allow us to get information based on transactions. The current products are
   just plain strings advicing what kind of Transaction it was, for example a Transaction of type "visit" should
   contain the park being visited by the customer. Purchases will be associated to a Product or SKU and so on.

I know this is very far from an actually secure and robust API but I wanted to see how far I could go in about 2 hours
and no more.

Thanks for reviewing it.
#!/bin/bash
# ===========================================
#  CRM API - Populate Customers & Transactions
# ===========================================

CUSTOMER_API="http://localhost:8080/api/customers"
TRANSACTION_API="http://localhost:8080/api/opportunity"

CUSTOMER_COUNT=10
TRANSACTIONS_PER_CUSTOMER=5

# Helper: generate ISO-8601 timestamps cross-platform
generate_timestamp() {
  if date --version >/dev/null 2>&1; then
    # GNU date (Linux)
    date -d "$1 days ago" --utc +%Y-%m-%dT%H:%M:%SZ
  else
    # BSD date (macOS)
    date -v-"$1"d -u +"%Y-%m-%dT%H:%M:%SZ"
  fi
}

# Transaction types
TYPES=("PURCHASE" "ATTEMPT" "VISIT" "COMPLAIN" "SKIP")

echo "🚀 Creating $CUSTOMER_COUNT mock customers and transactions..."

# --- Create Customers ---
for i in $(seq 1 $CUSTOMER_COUNT); do
  NAME="Customer $i"
  EMAIL="customer${i}@example.com"
  PHONE="555-00$(printf '%03d' $i)"

  JSON=$(jq -n --arg name "$NAME" --arg email "$EMAIL" --arg phone "$PHONE" \
    '{name: $name, email: $email, phone: $phone}')

  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$CUSTOMER_API" \
    -H "Content-Type: application/json" -d "$JSON")

  if [ "$RESPONSE" -eq 200 ] || [ "$RESPONSE" -eq 201 ]; then
    echo "✅ Customer created: $NAME"

    # --- Create Transactions for Customer ---
    CUSTOMER_ID=$i
    for j in $(seq 1 $TRANSACTIONS_PER_CUSTOMER); do
      TYPE=${TYPES[$((RANDOM % ${#TYPES[@]}))]}
      VALUE="Product ${RANDOM}"
      DAYS_AGO=$((RANDOM % 30))
      TIMESTAMP=$(generate_timestamp "$DAYS_AGO")

      TX_JSON=$(jq -n \
        --argjson customerId "$CUSTOMER_ID" \
        --arg type "$TYPE" \
        --arg timestamp "$TIMESTAMP" \
        --arg value "$VALUE" \
        '{customerId: $customerId, type: $type, timestamp: $timestamp, value: $value}')

      TX_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$TRANSACTION_API" \
        -H "Content-Type: application/json" -d "$TX_JSON")

      if [ "$TX_RESPONSE" -eq 200 ] || [ "$TX_RESPONSE" -eq 201 ]; then
        echo "   💾 Added transaction ($TYPE, $TIMESTAMP)"
      else
        echo "   ❌ Failed to add transaction #$j (HTTP $TX_RESPONSE)"
      fi
    done
  elif [ "$RESPONSE" -eq 409 ]; then
    echo "⚠️  Skipped duplicate email: $EMAIL"
  else
    echo "❌ Failed to add customer $NAME (HTTP $RESPONSE)"
  fi
done

echo "🎉 Done populating mock data!"

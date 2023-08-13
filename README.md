# How to Run 

Execute script `run_dbs.sh` which will launch three databases one for earch
service.

Then execute

```
./gradlew :order-service:bootRun
./gradlew :product-service:bootRun
./gradlew :user-service:bootRun
```

# Check service-ft module for Functional tests

# Considerations

Order Table does not have one to many relationship with products to describe
a quantity relationship meaning that orders do not have product quantity notion.

It was done as such for simplicity purpose

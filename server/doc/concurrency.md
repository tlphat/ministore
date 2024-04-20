# Concurrency Test

## Context

This document summarizes how we perform concurrency testing for ministore's current supported operations.
Further modification will be made for future feature updates.

## List operations

Race condition can happen when we have multiple clients perform push/pop/get to the same list at the same time.

For our [list concurrency test](../src/integration-test/java/name/tlphat/ministore/server/ListConcurrencyTest.java), we simulated 10 threads performing push operation at the same time. After that, we created another 10 threads performing pop operation and check at the data got popped.
We also verified that there are no more elements in the list.

---
layout: page
title: Xing Yu's Project Portfolio Page
---

## Project: Inventory Book (OneShelf)

OneShelf is an app designed as a one-stop platform for restaurant managers to handle their management needs such as having efficient inventory management 
and up-to-date information on pending deliveries, which translates to savings in man-hours and minimal losses due to over stocking.

Given below are my contributions to the project.

* **Refactoring Address Book 3**: Refactored Person into Item. (Pull request [\#37](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/37))
  * What it does: Allows CS2103T-T12-1 to start implementing proposed features tailored to our requirements.
  * Justification: This is an essential first step before any features can be implemented as Person class from AB3 is not suitable for our implementation. Variables such as Address and Email has to be removed. Quantity and Supplier have to be added as well.
  * Credits: *AB3 from CS2103T [Repo link](https://github.com/nus-cs2103-AY2021S1/tp)*

* **Added Max Quantity Tests**: Added tests for Max Quantity (Pull request [\#88](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/88))

* **Added DeliveryModel**: Added Delivery Model. (Pull request [\#103](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/103))
  * What it does: Acts as the skeleton for delivery class and commands to be implemented by other memebers.
  * Justification: This is an essential first step before any delivery features can be implemented.

* **Implemented Command History Traversal**: Implemented Command History Traversal (Pull request [\#125](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/125))
  * What it does: Allows user to traverse their command history with arrow up and down button from GUI.
  * Justification: Synergies our fast typist use case.

* **Added HistoryManager Tests**: Added tests for HistoryManager. (Pull request [\#125](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/125))

* **Implemented Sorting to ItemList**: Implemented sorting for Items. 
  * What it does: Items are sorted based on their % maxQuantity, if maxQuantity doesn't exist items are flushed to the end of the list. If 2 items have the same quantity, they are sorted lexicographically. (Pull request [\#145](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/145))
  * Justification: Sorting helps with inventory management and tracking what items require restocking.

* **Enhancements to existing features**:
  * Changed AddCommand to add on to existing items instead of throwing an error. An item is considered the same if they have the same name and supplier. (Pull request [\#44](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/44))
  * Fix error message thrown when INDEX provided by user is not correct. From Invalid Command Format to Index Provided is invalid. (Pull request [\#238](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/238))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `add` and `find`
    * Added documentation for `HistoryManager`
    * Added subheaders for UG for easier navigation
    * Added FAQ question "Help! I've accidentally typed `clear-i` or `clear-d` and wiped all my data!"
    * Added examples for `add-i` and `add-d`

  * Developer Guide:
    * Added Target user profile, value proposition and user stories.
    * Added Command History Traversal under implementation
    * Redesigned LogicClassDiagram.puml
    * Redesigned LogicSequenceDiagram.puml
    * Redesigned DeleteSequenceDiagram.puml
    * Updated Logic API documentation
    * Added AddItemActivityDiagram
    * Added CommandHistoryTraversalSequenceDiagram
    * Added "Adding an item manual test cases"

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=xnoobftw&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Community**:
  * PRs reviewed (with non-trivial review comments): 
  [\#54](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/54) 
  [\#87](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/87) 
  [\#141](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/141) 
  [\#147](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/147) 
  [\#163](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/163)
  [\#192](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/192)
  [\#207](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/207)
  * Reported bugs (such as: [GUI bug](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/47))

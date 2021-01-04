---
layout: page
title: Bobby Law's Project Portfolio Page
---

## Project: Inventory Book (OneShelf)

OneShelf is an app designed as a one-stop platform for restaurant managers to handle their management needs such as having efficient inventory management 
and up-to-date information on pending deliveries, which translates to savings in man-hours and minimal losses due to over stocking.

Given below are my contributions to the project.

* **Refactoring AddressBook to InventoryBook** Refactored AddressBook to InventoryBook (Pull Request [\#53](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/53))
  * What it does: Allows readability to suit our application.
  * Justification: To allow our application to make more sense with class names and method names.
  * Credits: *AB3 from CS2103T [Repo link](https://github.com/nus-cs2103-AY2021S1/tp)*

* **Added Storage for Deliveries** Added Storage (Pull Request [\#107](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/107))
  * What it does: Convert a delivery into json and to be stored locally.
  * Justification: Allows the user to be able to save their deliveries.

* **Added Delivery Add Command** Allows user to input `add-d` command
  * What it does: Allows the adding of pending deliveries into OneShelf.
  * Justification: A restaurant manager would want to be able add their pending deliveries.
  * (Pull Request [#140](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/140))

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=zeranium97&tabRepo=AY2021S1-CS2103T-T12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code~test-code)

* **Enhancements to existing features**:
  * Remove Supplier as a mandatory field. It will display "NO SUPPLIER" if input is empty. (Pull Request [\#43](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/43))
  * Twerk test's data and method to suit our program (Pull Request [\#52](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/52))
  * Modify `find` to support finding based on different data fields (Pull Request [\#86](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/86))
  * Further enhance `find` to search a combination of fields (Pull Request [\#171](https://github.com/AY2021S1-CS2103T-T12-1/tp/pull/86))

<div style="page-break-after: always;"></div>

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete` and `Coming Soon`.
    * Updated documentation for `find` feature.

  * Developer Guide:
    * Added Use Cases.
    * Updated Model section and UML Diagram.
    * Updated Storage section and UML Diagram.
    * Added `find` implementation including an activity diagram and sequence diagram.
    * Added `edit` implementation including a class, activity and sequence diagram.

* **Community**
   * Reported bugs (such as: [Unhandled Exceptions](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/78)

* **Tools**:
  * Update site-wide settings using Jekyll


# Smart Pantry Manager

## 1. Project Overview

This assignment is a Smart Pantry Manager Java-based Android application created to assist users in managing food items or leftovers stored in their pantry.

The application suggests recipes that can be made from ingredients that are available in the pantry. The Smart Pantry Manager application allows a user to add, view, edit, and delete food items in their pantry. It also monitors food expiry dates.

The main goal of the Smart Pantry Manager application is to reduce food waste and assist users in making use of ingredients already available in their pantry.

## 2. Objectives

The Smart Pantry Manager objectives are to:

* Allow users to maintain and manage their own digital pantry food items.
* Store food information, including units such as kg, g, and item, as well as expiry dates.
* Allow food items to be added, edited, and deleted from the pantry.
* Detect food item expiry dates.
* Show available recipes based on ingredients in the pantry.
* Display recipe ingredients and instructions on how to prepare each recipe.
* Store application data using an Android SQLite database.
* Provide navigation between the different screens of the application.

## 3. Features

Users are able to:

* Add food items.
* View stored food items.
* Edit food items.
* Delete food items from the pantry.
* Store food-related information in the database.

### Expiry Detection

The Food Expiry screen detects food item expiry dates and labels them as:

* **Expired**
* **Expires Soon**
* **Not Expired**
* **No Food Expiry Date**

Items labelled as **Expires Soon** are within seven days of their expiry date.

### Recipes Available

The application has 15 preloaded recipes divided into:

* 5 bread recipes
* 5 noodle recipes
* 5 cracker recipes

The available recipe system checks the ingredients and quantities stored in the pantry food item list. A recipe only becomes available when the user has all the required ingredients and sufficient quantities for the recipe.

Ingredient names and units are normalised to improve the matching between pantry food items and recipe requirements.

### Recipe Details

Users can select a recipe and view:

* The name of the recipe.
* All the ingredients required.
* The quantity required.
* Preparation instructions for the recipe.

### Settings

The application has a Settings screen for options related to the application.

### Navigation

The application allows navigation between all application screens using the application's navigation controls and menu options.

## 4. Different Screens

The application contains the following screens:

### Main Screen

Provides access to the main functions of the application.

### Add Foods

Allows a user to enter a new food item.

### View Foods

Shows food items stored in the pantry and allows users to edit food items.

### Food Expiry

Displays expiry date information and expiry status for food items.

### Available Recipes

Displays available recipes based on the current food items in the digital pantry.

### Detailed Recipe

Displays the ingredients and preparation steps of a selected recipe.

### Settings

Provides access to the settings of the application.

## 5. Recipe Matching

The Smart Pantry Manager has a strict ingredient-matching system which requires all ingredients and required quantities to be available in the pantry before a recipe appears as available.

For example, if a recipe requires:

* **Bread:** 2 items
* **Jam:** 1 item

The pantry must contain at least these required quantities for the recipe to become available.

## 6. Database

The application uses an Android SQLite database for local data storage and stores:

* Food items
* Recipes
* Recipe ingredients
* Recipe quantities
* Units for recipes
* Expiry dates for food items

The database is managed through the `Pantry_DB.java` class.

## 7. Technologies

The application uses:

* Java
* Android Studio
* Android SDK
* XML layouts
* SQLite
* Android Activities
* RecyclerView/ListView
* Java Adapters
* Intents for screen navigation

## 8. Project Structure

The application uses Java classes that are responsible for application screens, database management, models, and adapters.

Important classes include:

* `Main_Screen.java`
* `Add_Foods.java`
* `View_Foods.java`
* `Edit_Foods.java`
* `Food_Expiry.java`
* `Available_Recipe.java`
* `Detailed_Recipe.java`
* `Screen_Settings.java`
* `Pantry_DB.java`
* `Pantry_Foods.java`
* `Recipes.java`
* `Recipe_Ingredients.java`
* `Adapter_Recipe.java`

XML layout files are also used to create the user interface of the application.

## 9. Data Validation

The application performs validation checks when food information is entered and before it is saved.

Quantity and unit information are particularly important because they are used when determining whether recipes are available based on the ingredients stored in the pantry.

## 10. How to Run the Application

1. Open Android Studio.
2. Open the Smart Pantry Manager project.
3. Allow Android Studio to synchronise the project.
4. Start an Android emulator or connect a compatible Android device.
5. Run the application using the **Run** button in Android Studio.
6. Use the Main Screen to access the different application functions.

## 11. Basic Usage

### Adding a Food Item

1. Click the **Add Foods** button.
2. Enter the food name.
3. Enter the food category.
4. Enter the quantity.
5. Select the unit.
6. Enter an expiry date.
7. Save the food item.

### Managing Pantry Items

Click **View Foods** to see stored food items.

Existing items can also be edited by clicking on the food item.

### Checking Expiry Dates

Open **Food Expiry** to view the expiry status of food items.

### Finding Recipes

Click **Available Recipes**.

The application checks the food items in the pantry against the recipe requirements and then displays the available recipes.

Click on a recipe to open its details.

## 12. Recipe Categories

The application has 15 recipes divided into three categories:

* **Bread Recipes**
* **Noodle Recipes**
* **Cracker Recipes**

## 13. Testing

The application has been tested to check whether it can:

* Add food items.
* View food items.
* Edit food items.
* Delete food items.
* Detect expiry dates.
* Match recipe ingredients.
* Match recipe quantities.
* Navigate to recipe details.
* Navigate between screens.
* Store data in the database.

## 14. GitHub Repository

The source code for the project is maintained in the project's GitHub repository.

**Repository:**
[Smart Pantry Manager GitHub Repository](https://github.com/readwan/SmartPantry?utm_source=chatgpt.com)

## 15. Author

Smart Pantry Manager was developed as part of the Mobile App Development practical assignment.

**Developer:** Zoran Fisher
**Course:** BSc Information Technology – Mobile App Development
**Project:** Smart Pantry Manager

## Conclusion

Smart Pantry Manager provides a simple digital solution for managing food items in a pantry and identifying available recipes.

The application consists of pantry management, expiry tracking, and strict recipe matching. The application only suggests recipes when all the required ingredients and quantities are available in the user's digital pantry.

The application assists users in better managing food items already stored in their digital pantry and can help reduce unnecessary food waste.

# APIS
## AuthController
test: completed
1. http://localhost:8080/auth/signin -> register user
2. http://localhost:8080/auth/signup -> login user

## UserController
test: completed
1. http://localhost:8080/api/users/profile -> find user by jwt
2. http://localhost:8080/api/users/profile/{email} -> find user by email

## AdminRestaurantController
test: completed
1. http://localhost:8080/api/admin/restaurants/create -> create restaurant: PostMapping
2. http://localhost:8080/api/admin/restaurants/{restaurantId} -> update restaurant: PutMapping
3. http://localhost:8080/api/admin/restaurants/{restaurantId} -> delete restaurant: DeleteMapping
4. http://localhost:8080/api/admin/restaurants/{restaurantId}/status -> update restaurant status: PutMapping
5. http://localhost:8080/api/admin/restaurants/user -> find restaurant by userId: GetMapping

## RestaurantController
test: complete
1. http://localhost:8080/api/restaurants/search?parms= -> search restaurant by keyword: Get
2. http://localhost:8080/api/restaurants -> search all restaurants :Get
3. http://localhost:8080/api/restaurants/{restaurantId} -> find restaurant by restaurant id : Get
4. http://localhost:8080/api/restaurants/{restaurantId}/add-favourites -> add restaurant to favourite

## AdminFoodController
test: completed
1. http://localhost:8080/api/admin/food/create -> create food: Post
2. http://localhost:8080/api/admin/food/{foodId} -> delete food: Delete
3. http://localhost:8080/api/admin/food/{foodId} -> update food availability: Put

## FoodController
test: completed
1. http://localhost:8080/api/food/search?{} -> search food: Get
2. http://localhost:8080/api/food/restaurant/1?vegetarian=false&seasonal=true&nonveg=true&foodCategory=indian -> get restaurant's food: Get

## CategoryController
test: completed
1. http://localhost:8080/api/admin/category/create -> create ingredients category: Post
2. http://localhost:8080/api/admin/category/restaurant -> get category by restaurant id.
3. http://localhost:8080/api/category/{categoryId} -> get category by category id.

## IngredientsCategory
test: completed
1. http://localhost:8080/api/admin/ingredients/category/create -> create ingredient category: Post
2. http://localhost:8080/api/admin/ingredients/items/create -> create ingredients item: Post
3. http://localhost:8080/api/admin/ingredients/stock/{id} -> update ingredients stock: Put
4. http://localhost:8080/api/admin/ingredients/restaurant/{id} -> get restaurant ingredient: Get
5. http://localhost:8080/api/admin/ingredients/restaurant/{id}/category -> get restaurant ingredient category: Get

## CartController
test: completed
1. http://localhost:8080/api/cart/add -> add item to cart: Post
2. http://localhost:8080/api/cart-item/update -> update cart item: Put
3. http://localhost:8080/api/cart-item/{id}/remove -> remove cart item: Delete
4. http://localhost:8080/api/cart/clear -> clear all cart items: 
5. http://localhost:8080/api/cart -> get user cart 

## OrderController
test:
1. http://localhost:8080/api/order -> crate order: Post
2. http://localhost:8080/api/order/user -> get user's order: Get
3. http://localhost:8080/api/order/{orderId} -> cancel Order :Delete

## AdminOrderController
test:
1. http://localhost:8080/api/admin/order/restaurant/{id} -> get all order of restaurant: Get
2.  http://localhost:8080/api/admin/order/{orderId}/{orderStatus} -> update order status: Put


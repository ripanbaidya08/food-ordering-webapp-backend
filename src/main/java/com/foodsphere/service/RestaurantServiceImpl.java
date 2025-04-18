package com.foodsphere.service;

import com.foodsphere.dto.RestaurantDto;
import com.foodsphere.exception.RestaurantException;
import com.foodsphere.model.Address;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.Users;
import com.foodsphere.repository.AddressRepository;
import com.foodsphere.repository.RestaurantRepository;
import com.foodsphere.repository.UserRepository;
import com.foodsphere.request.CreateRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override public Restaurant createRestaurant(CreateRestaurantRequest restaurantRequest, Users user) {
        validateAddressFields(restaurantRequest.getAddress());

        Address address = addressRepository.save(restaurantRequest.getAddress());
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantRequest.getName());
        restaurant.setDescription(restaurantRequest.getDescription());
        restaurant.setCuisineType(restaurantRequest.getCuisineType());
        restaurant.setOpeningHours(restaurantRequest.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setAddress(address);

        restaurant.setContactInformation(restaurantRequest.getContactInformation());
        restaurant.setImages(restaurantRequest.getImages());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    private void validateAddressFields(Address address) {
        if (address.getCity() == null) throw new IllegalArgumentException("City cannot be null");
    }

    @Override public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws RestaurantException {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getName() != null)    restaurant.setName(updatedRestaurant.getName());
        if(restaurant.getDescription() != null)   restaurant.setDescription(updatedRestaurant.getDescription());
        if(restaurant.getAddress() != null)    restaurant.setAddress(updatedRestaurant.getAddress());
        if(restaurant.getCuisineType() != null)  restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        if(restaurant.getOpeningHours() != null)   restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());

        return restaurantRepository.save(restaurant);
    }

    @Override public void deleteRestaurant(Long restaurantId) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override public Restaurant findRestaurantById(Long restaurantId) throws RestaurantException {

        Optional<Restaurant> selectedRestaurant = restaurantRepository.findById(restaurantId);
        if(selectedRestaurant.isEmpty()) throw new RestaurantException("Restaurant not found with id: " + restaurantId);

        return selectedRestaurant.get();
    }

    @Override public Restaurant findRestaurantByUserId(Long userId) throws RestaurantException {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null) throw new RestaurantException("Restaurant not found with Owner id : " + userId);

        return restaurant;
    }

    @Override public RestaurantDto addToFavourites(Long restaurantId, Users user) throws RestaurantException {
        /*
        * checking : if it is present then remove it from the favourites or else add it.
         */
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto favoriteRestaurantDto = new RestaurantDto();

        favoriteRestaurantDto.setDescription(restaurant.getDescription());
        favoriteRestaurantDto.setImages(restaurant.getImages());
        favoriteRestaurantDto.setTitle(restaurant.getName());
        favoriteRestaurantDto.setId(restaurantId);

        boolean isFavourite = false ;
        List<RestaurantDto> favourites = user.getFavourites();
        for(RestaurantDto favourite : favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavourite = true;
                break;
            }
        }
        if(isFavourite){
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
        }else {
            favourites.add(favoriteRestaurantDto);
        }
        userRepository.save(user);

        return favoriteRestaurantDto;
    }

    @Override public Restaurant updateRestaurantStatus(Long id) throws RestaurantException {

        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}

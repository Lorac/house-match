package ca.ulaval.glo4003.housematch.domain.user;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class User extends UserObservable implements PropertyObserver {
    private static final String FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION = "The details of a property you favorited (%s) have changed.";
    private static final String PROPERTY_PHOTO_REJECTED_EVENT_DESCRIPTION
        = "The photo '%s' of your property for sale (%s) has been rejected.";

    static final Integer INACTIVITY_TIMEOUT_PERIOD_IN_MONTHS = 6;

    private NotificationFactory notificationFactory;
    private StringHasher stringHasher;

    private String username;
    private String email;
    private String passwordHash;
    private UserRole role;
    private UserStatus status = UserStatus.INACTIVE;
    private UUID activationCode;
    private Boolean activated = false;
    private ZonedDateTime lastLoginDate;
    private Set<Property> propertiesForSale = new HashSet<>();
    private Set<Property> purchasedProperties = new HashSet<>();
    private Set<Property> favoriteProperties = new HashSet<>();
    private Queue<Notification> notificationsQueue = new ConcurrentLinkedQueue<>();
    private NotificationSettings notificationSettings = new NotificationSettings();
    private Address address;

    public User(final NotificationFactory notificationFactory, final StringHasher stringHasher, final String username, final String email,
            final String password, final UserRole role) {
        this.notificationFactory = notificationFactory;
        this.stringHasher = stringHasher;
        this.username = username;
        this.email = email;
        this.passwordHash = stringHasher.hash(password);
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UUID getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(UUID activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<Property> getPropertiesForSale() {
        return propertiesForSale;
    }

    public void setPropertiesForSale(Set<Property> properties) {
        propertiesForSale = properties;
    }

    public Set<Property> getPurchasedProperties() {
        return purchasedProperties;
    }

    public void setPurchasedProperties(Set<Property> properties) {
        purchasedProperties = properties;
    }

    public Set<Property> getFavoriteProperties() {
        return favoriteProperties;
    }

    public void setFavoriteProperties(Set<Property> favoriteProperties) {
        this.favoriteProperties = favoriteProperties;
    }

    public NotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public Queue<Notification> getNotificationQueue() {
        return notificationsQueue;
    }

    public void setNotificationQueue(Queue<Notification> notificationsQueue) {
        this.notificationsQueue = notificationsQueue;
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public ZonedDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(ZonedDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean hasRole(UserRole role) {
        return this.role.equals(role);
    }

    public void validatePassword(String password) throws InvalidPasswordException {
        if (!this.passwordHash.equals(stringHasher.hash(password))) {
            throw new InvalidPasswordException("Password does not match.");
        }
        lastLoginDate = ZonedDateTime.now();
        applyUserStatusPolicy();
    }

    public void updateEmail(String email) {
        this.email = email;
        activated = false;
    }

    public void activate() {
        activated = true;
        activationCode = null;
    }

    public void addPropertyForSale(Property property) {
        propertiesForSale.add(property);
        property.markForSale();
        property.registerObserver(this);
        applyUserStatusPolicy();
    }

    public Property getPropertyForSaleByHashCode(int hashCode) throws PropertyNotFoundException {
        try {
            return propertiesForSale.stream().filter(p -> p.hashCode() == hashCode).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new PropertyNotFoundException(String.format("Cannot find property with hashcode '%s' belonging to this user.", hashCode));
        }
    }

    public void addPropertyToFavorites(Property property) {
        favoriteProperties.add(property);
        property.registerObserver(this);
    }

    public Boolean isPropertyFavorited(Property property) {
        return favoriteProperties.contains(property);
    }

    public void applyUserStatusPolicy() {
        if (role == UserRole.BUYER && !isActiveAsBuyer()) {
            changeStatus(UserStatus.INACTIVE);
        } else if (role == UserRole.SELLER && !isActiveAsSeller()) {
            changeStatus(UserStatus.INACTIVE);
        } else {
            changeStatus(UserStatus.ACTIVE);
        }
    }

    private Boolean isActiveAsBuyer() {
        return lastLoginDate != null && lastLoginDate.isAfter(ZonedDateTime.now().minusMonths(INACTIVITY_TIMEOUT_PERIOD_IN_MONTHS));
    }

    private Boolean isActiveAsSeller() {
        return propertiesForSale.size() > 0;
    }

    private void changeStatus(UserStatus newStatus) {
        if (status != newStatus) {
            status = newStatus;
            userStatusChanged(this, newStatus);
        }
    }

    public void purchaseProperty(Property property) {
        purchasedProperties.add(property);
        property.markAsSold();
    }

    public void notify(Notification notification) {
        if (notificationSettings.isNotificationEnabled(notification.getType())) {
            notificationsQueue.add(notification);
            userNotificationQueued(this, notification);
        }
    }

    @Override
    public int hashCode() {
        return username.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        User user = (User) obj;
        return new EqualsBuilder().append(username.toLowerCase(), user.getUsername().toLowerCase()).isEquals();
    }

    public boolean usernameEquals(String username) {
        return this.username.equalsIgnoreCase(username);
    }

    @Override
    public void propertyStatusChanged(Object sender, PropertyStatus newStatus) {
        // Event intentionally ignored.
    }

    @Override
    public void propertyDetailsChanged(Object sender, PropertyDetails newPropertyDetails) {
        Property property = (Property) sender;
        String eventDescription = String.format(FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION, property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.FAVORITE_PROPERTY_MODIFIED, eventDescription);
        notify(notification);
    }

    @Override
    public void propertyPhotoRejected(Object sender, PropertyPhoto propertyPhoto) {
        Property property = (Property) sender;
        String eventDescription = String.format(PROPERTY_PHOTO_REJECTED_EVENT_DESCRIPTION, propertyPhoto.toString(), property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.PROPERTY_PHOTO_REJECTED, eventDescription);
        notify(notification);
    }
}

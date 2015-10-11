package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyListingDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UserRole ANOTHER_SAMPLE_ROLE = UserRole.BUYER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String ANOTHER_SAMPLE_PASSWORD = "PASSWORD5678";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ANOTHER_SAMPLE_EMAIL = "email2@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_CAPITALIZED_USERNAME = "ALICE";
    private static final String ANOTHER_SAMPLE_USERNAME = "Bob";
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final Object SAMPLE_OBJECT = new Object();

    private Property propertyMock;
    private PropertyListingDetails propertyDetailsMock;
    private PropertyListingDetails propertyDetailsMock2;

    private List<Property> propertyListings;
    private User user;

    @Before
    public void init() throws Exception {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyListingDetails.class);
        propertyDetailsMock2 = mock(PropertyListingDetails.class);
        propertyListings = new ArrayList<>();
        user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void usersWithTheSameUsernameShouldBeConsideredAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usersWithDifferentUsernamesShouldBeConsideredAsDifferent() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void usersWithTheSameUsernameShouldHaveTheSameHashCode() throws Exception {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    public void usersWithDifferentUsernamesShouldNotHaveTheSameHashCode() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertNotEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldBeConsideredAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void userComparedWithItselfShouldBeConsideredAsEqual() {
        assertTrue(user.equals(user));
    }

    @Test
    public void userComparedWithAnotherObjectShouldNotBeConsideredAsEqual() {
        assertFalse(user.equals(SAMPLE_OBJECT));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersWithTheSameUsernameAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersWithDifferentUsernameAsDifferent() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void settingTheUsernameSetsTheSpecifiedUsername() throws Exception {
        user.setUsername(ANOTHER_SAMPLE_USERNAME);
        assertEquals(ANOTHER_SAMPLE_USERNAME, user.getUsername());
    }

    @Test
    public void settingTheEmailSetsTheSpecifiedEmail() throws Exception {
        user.setEmail(ANOTHER_SAMPLE_EMAIL);
        assertEquals(ANOTHER_SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void settingThePasswordSetsTheSpecifiedPassword() throws Exception {
        user.setPassword(ANOTHER_SAMPLE_PASSWORD);
        assertEquals(ANOTHER_SAMPLE_PASSWORD, user.getPassword());
    }

    @Test
    public void validationOfTheRightPasswordDoesNotThrowAnException() throws Exception {
        user.validatePassword(SAMPLE_PASSWORD);
    }

    @Test
    public void validationOfTheWrongPasswordThrowsInvalidPasswordException() throws Exception {
        assertFalse(user.validatePassword(ANOTHER_SAMPLE_PASSWORD));
    }

    @Test
    public void settingTheRoleSetsTheSpecifiedRole() {
        user.setRole(ANOTHER_SAMPLE_ROLE);
        assertEquals(ANOTHER_SAMPLE_ROLE, user.getRole());
    }

    @Test
    public void userShouldNotHaveTheSpecifiedRoleWhenThatRoleHasNotBeenAdded() {
        user.setRole(SAMPLE_ROLE);
        assertFalse(user.hasRole(ANOTHER_SAMPLE_ROLE));
    }

    @Test
    public void userShouldHaveTheSpecifiedRoleWhenThatRoleHasBeenSet() {
        user.setRole(ANOTHER_SAMPLE_ROLE);
        assertTrue(user.hasRole(ANOTHER_SAMPLE_ROLE));
    }

    @Test
    public void updatingTheEmailUpdatesTheSpecifiedEmail() {
        user.updateEmail(ANOTHER_SAMPLE_EMAIL);
        assertEquals(ANOTHER_SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void updatingTheEmailSetsTheActivationFlagToFalse() {
        user.updateEmail(ANOTHER_SAMPLE_EMAIL);
        assertFalse(user.isActivated());
    }

    @Test
    public void settingTheActivationCodeSetsTheSpecifiedActivationCode() {
        user.setActivationCode(SAMPLE_ACTIVATION_CODE);
        assertEquals(SAMPLE_ACTIVATION_CODE, user.getActivationCode());
    }

    @Test
    public void newUserIsNotActivatedByDefault() {
        assertFalse(user.isActivated());
    }

    @Test
    public void settingActivationFlagSetsTheAcvtivationFlag() {
        user.setActivated(Boolean.TRUE);
        assertEquals(Boolean.TRUE, user.isActivated());
    }

    @Test
    public void activatingTheUserActivatesTheUser() throws Exception {
        user.activate();
        try {
            user.validateActivation();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void activatingTheUserClearsTheActivationCode() throws Exception {
        user.setActivationCode(SAMPLE_ACTIVATION_CODE);
        user.activate();
        assertNull(user.getActivationCode());
    }

    @Test(expected = UserNotActivatedException.class)
    public void activationValidationOnNonActivatedUserThrowsUserNotActivatedException() throws Exception {
        user.validateActivation();
    }

    @Test
    public void settingThePropertyListingsSetsTheSpecifiedPropertyListings() {
        user.setPropertyListings(propertyListings);
        assertEquals(propertyListings, user.getPropertyListings());
    }

    @Test
    public void addingAPropertyListingAddsTheSpecifiedPropertyListing() {
        user.addPropertyListing(propertyMock);
        assertThat(user.getPropertyListings(), contains(propertyMock));
    }

    @Test(expected = UserPropertyNotListedException.class)
    public void updatingPropertyNotExistingInUserThrowsUserPropertyNotListed() throws Exception {
        user.updateProperty(propertyMock);
    }

    @Test
    public void updatingPropertyUpdatePropertyInList() throws Exception {
        Property property1 = propertyMock;
        when(property1.getPropertyDetails()).thenReturn(propertyDetailsMock);
        Property property2 = propertyMock;
        when(property2.getPropertyDetails()).thenReturn(propertyDetailsMock2);
        user.addPropertyListing(property1);

        user.updateProperty(property2);
        assertEquals(user.getPropertyListings().size(), 1);
        assertEquals(user.getPropertyListings().get(0).getPropertyDetails(), propertyDetailsMock2);

    }
}
package ca.ulaval.glo4003.housematch.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.web.viewmodels.NotificationSettingsFormViewModel;

public class NotificationSettingsConverterTest {

    private static final NotificationInterval SAMPLE_NOTIFICATION_INTERVAL = NotificationInterval.DAILY;

    private NotificationSettingsFormViewModel notificationSettingsFormViewModelMock;
    private NotificationSettings notificationSettingsMock;
    private NotificationSettingsConverter converter;

    @Before
    public void init() {
        initMocks();
        initStubs();
        converter = new NotificationSettingsConverter();
    }

    private void initMocks() {
        notificationSettingsFormViewModelMock = mock(NotificationSettingsFormViewModel.class);
        notificationSettingsMock = mock(NotificationSettings.class);
    }

    private void initStubs() {
        when(notificationSettingsMock.getNotificationInterval(NotificationType.PROPERTY_PUT_UP_FOR_SALE))
                .thenReturn(SAMPLE_NOTIFICATION_INTERVAL);
        when(notificationSettingsMock.getNotificationInterval(NotificationType.FAVORITE_PROPERTY_CHANGED))
                .thenReturn(SAMPLE_NOTIFICATION_INTERVAL);

        when(notificationSettingsFormViewModelMock.getFavoritePropertyModificationNotificationInterval())
                .thenReturn(SAMPLE_NOTIFICATION_INTERVAL);
        when(notificationSettingsFormViewModelMock.getPropertyPutUpForSaleNotificationInterval()).thenReturn(SAMPLE_NOTIFICATION_INTERVAL);
    }

    @Test
    public void convertsTheViewModelFromTheSpecifiedNotificationSettingsObject() {
        NotificationSettingsFormViewModel viewModel = converter.convert(notificationSettingsMock);

        assertEquals(SAMPLE_NOTIFICATION_INTERVAL, viewModel.getPropertyPutUpForSaleNotificationInterval());
        assertEquals(SAMPLE_NOTIFICATION_INTERVAL, viewModel.getFavoritePropertyModificationNotificationInterval());
    }

    @Test
    public void convertsTheNotificationSettingsObjectFromTheSpecifiedNotificationSettingsFormViewModel() {
        NotificationSettings notificationSettings = converter.convert(notificationSettingsFormViewModelMock);

        assertEquals(SAMPLE_NOTIFICATION_INTERVAL, notificationSettings.getNotificationInterval(NotificationType.PROPERTY_PUT_UP_FOR_SALE));
        assertEquals(SAMPLE_NOTIFICATION_INTERVAL, notificationSettings.getNotificationInterval(NotificationType.PROPERTY_PUT_UP_FOR_SALE));
    }

}

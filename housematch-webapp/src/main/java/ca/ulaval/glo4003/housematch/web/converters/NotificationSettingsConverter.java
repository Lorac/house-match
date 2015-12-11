package ca.ulaval.glo4003.housematch.web.converters;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.web.viewmodels.NotificationSettingsFormViewModel;

public class NotificationSettingsConverter {

    public NotificationSettingsFormViewModel convert(NotificationSettings notificationSettings) {
        NotificationSettingsFormViewModel viewModel = new NotificationSettingsFormViewModel();
        viewModel.setPropertyPutUpForSaleNotificationInterval(
                notificationSettings.getNotificationInterval(NotificationType.PROPERTY_PUT_UP_FOR_SALE));
        viewModel.setFavoritePropertyModificationNotificationInterval(
                notificationSettings.getNotificationInterval(NotificationType.FAVORITE_PROPERTY_CHANGED));
        return viewModel;
    }

    public NotificationSettings convert(NotificationSettingsFormViewModel viewModel) {
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setNotificationInterval(NotificationType.PROPERTY_PUT_UP_FOR_SALE,
                viewModel.getPropertyPutUpForSaleNotificationInterval());
        notificationSettings.setNotificationInterval(NotificationType.FAVORITE_PROPERTY_CHANGED,
                viewModel.getFavoritePropertyModificationNotificationInterval());
        return notificationSettings;
    }

}

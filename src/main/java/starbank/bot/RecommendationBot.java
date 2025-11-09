package starbank.bot;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import starbank.user.User;
import starbank.user.User;
import starbank.user.UserService;
import starbank.recommendation.RecommendationService;

@Component
public class RecommendationBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final RecommendationService recommendationService;

    @Value("${telegram.bot.username}")
    private String botUsername;

    public RecommendationBot(@Value("${telegram.bot.token}") String botToken,
                             UserService userService, RecommendationService recommendationService) {
        super(botToken);
        this.userService = userService;
        this.recommendationService = recommendationService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/recommend ")) {
                String username = messageText.substring("/recommend ".length());
                handleRecommendCommand(chatId, username);
            } else {
                handleStartOrHelp(chatId);
            }
        }
    }

    private void handleStartOrHelp(long chatId) {
        String text = "Добро пожаловать в StarBank! \n\n" +
                "Чтобы получить персональные рекомендации по продуктам, используйте команду: \n" +
                "`/recommend <имя_пользователя>`";
        sendMessage(chatId, text);
    }

    private void handleRecommendCommand(long chatId, String username) {

        List<User> users = userService.findByUsername(username);

        if (users.size() != 1) {
            sendMessage(chatId, "Пользователь не найден.");
            return;
        }

        User user = users.get(0);

        List<Product> recommendations = recommendationService.getRecommendations(user);

        StringBuilder responseText = new StringBuilder();
        responseText.append("Здравствуйте, ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("!\n\n");
        responseText.append("Новые продукты для вас:\n");

        if (recommendations.isEmpty()) {
            responseText.append("На данный момент для вас нет специальных предложений.");
        } else {
            recommendations.forEach(product ->
                    responseText.append("- ").append(product.getName()).append("\n")
            );
        }

        sendMessage(chatId, responseText.toString());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            // Логирование ошибки
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
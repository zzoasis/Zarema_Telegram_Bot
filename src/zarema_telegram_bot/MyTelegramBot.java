package zarema_telegram_bot;

import java.io.File;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyTelegramBot extends TelegramLongPollingCommandBot {

    public static SendPhoto getDefaultMessage(String chatId) {
        return SendPhoto
                .builder()
                .chatId(chatId)
                .photo(new InputFile(new File("5variant.png").getAbsoluteFile()))
                .parseMode(ParseMode.MARKDOWN)
                .caption("Чтобы решить уравнение необходимо ввести команду '/solve', где "
                        + "первый аргумент это 'x', второй это 'a', а третий 'b'\n\n"
                        + "Например, если 'x' = 2, 'a' = 3, 'b' = 4: '/solve 2 3 4'")
                .build();
    }

    public MyTelegramBot() {

        register(new SolveCommand());

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text("Команда " + message.getText() + " неизвестна этому боту.")
                    .build();
            try {
                absSender.execute(commandUnknownMessage);
                absSender.execute(getDefaultMessage(message.getChatId().toString()));
            } catch (TelegramApiException e) {
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            try {
                execute(getDefaultMessage(message.getChatId().toString()));
            } catch (TelegramApiException e) {
            }
        }
    }

    //Получаем никнейм бота
    @Override
    public String getBotUsername() {
        return "Zarema_Telegram_Bot";
    }

    //Получаем токен бота
    @Override
    public String getBotToken() {
        return "1648485956:AAEo-WsN4k1qbvBsnCO2OEgkjghBlU0yEVM";
    }

}
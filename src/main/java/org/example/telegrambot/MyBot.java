package org.example.telegrambot;

import org.example.interfaces.Steps;
import org.example.model.User;
import org.example.model.UserStep;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.service.ExcelWriter.write;


public class MyBot extends TelegramLongPollingBot {

    private final String CHANNEL_ID1 = "@Volunteers_uz";
    private final String CHANNEL_ID2 = "@Muzaffarov_Sanatbek";
    private final String botToken;
    private final String botUsername;
    private User user = User.builder().build();
    private List<UserStep> users = new ArrayList<>();


    public MyBot(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
        if (isUserMemberOfChannel(update.getMessage().getChat().getId(), CHANNEL_ID1)
                && isUserMemberOfChannel(update.getMessage().getChat().getId(), CHANNEL_ID2)) {
            if (update.hasMessage()) {
                if (update.getMessage().hasText()) {

                    String text = update.getMessage().getText();
                    String chatId = String.valueOf(update.getMessage().getChatId());
                    UserStep userStep = saveUser(chatId);

                    if (text.equals("/start")) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Assalomu alaykum, EKO Zakovat loyihasiga hush kelibsiz!");

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);

                        List<KeyboardRow> keyboardRows = new ArrayList<>();

                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        KeyboardButton keyboardButton1 = new KeyboardButton("Til\uD83C\uDF10");

                        keyboardRow1.add(keyboardButton1);
                        keyboardRows.add(keyboardRow1);

                        replyKeyboardMarkup.setKeyboard(keyboardRows);
                        message.setReplyMarkup(replyKeyboardMarkup);
                        userStep.setStepName(Steps.LANGUAGE);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }



                    else if ((text.equals("Til\uD83C\uDF10")
                            && userStep.getStepName().equals(Steps.LANGUAGE))
                            || text.equals("Tillar ro'yhatiga qaytish↩️")
                            || text.equals("Back to Languages' list↩️")
                            || text.equals("Вернуться к списку языков↩️")) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            Tilni tanlang
                            Choose language
                            Выберите язык
                            """);

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);

                        List<KeyboardRow> keyboardRows = new ArrayList<>();

                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        KeyboardButton keyboardButton1 = new KeyboardButton("O'zbek\uD83C\uDDFA\uD83C\uDDFF");
                        KeyboardButton keyboardButton2 = new KeyboardButton("English\uD83C\uDDFA\uD83C\uDDF8");
                        KeyboardButton keyboardButton3 = new KeyboardButton("Русский\uD83C\uDDF7\uD83C\uDDFA");

                        keyboardRow1.add(keyboardButton1);
                        keyboardRow1.add(keyboardButton2);
                        keyboardRow1.add(keyboardButton3);
                        keyboardRows.add(keyboardRow1);

                        replyKeyboardMarkup.setKeyboard(keyboardRows);
                        message.setReplyMarkup(replyKeyboardMarkup);
                        userStep.setStepName(Steps.BOLIM);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }



                    else if (text.equals("O'zbek\uD83C\uDDFA\uD83C\uDDFF")
                            && userStep.getStepName().equals(Steps.BOLIM)) {
                        userStep.setStepLang(Steps.UZBEK);
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Kerakli bo'limni tanlang:");

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);

                        List<KeyboardRow> keyboardRows = new ArrayList<>();

                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        KeyboardRow keyboardRow2 = new KeyboardRow();
                        KeyboardRow keyboardRow3 = new KeyboardRow();
                        KeyboardRow keyboardRow4 = new KeyboardRow();
                        KeyboardButton keyboardButton1 = new KeyboardButton("Eko-zakovatga ro'yhatdan o'tish\uD83D\uDCDD");
                        KeyboardButton keyboardButton2 = new KeyboardButton("Eko-zakovat haqida ma'lumot olish\uD83D\uDCC4");
                        KeyboardButton keyboardButton3 = new KeyboardButton("Oltin Qanot Volontiyorlari haqida\uD83C\uDF1E");
                        KeyboardButton keyboardButton4 = new KeyboardButton("Tillar ro'yhatiga qaytish↩️");

                        keyboardRow1.add(keyboardButton1);
                        keyboardRow2.add(keyboardButton2);
                        keyboardRow3.add(keyboardButton3);
                        keyboardRow4.add(keyboardButton4);
                        keyboardRows.add(keyboardRow1);
                        keyboardRows.add(keyboardRow2);
                        keyboardRows.add(keyboardRow3);
                        keyboardRows.add(keyboardRow4);

                        replyKeyboardMarkup.setKeyboard(keyboardRows);
                        message.setReplyMarkup(replyKeyboardMarkup);
                        userStep.setStepName(Steps.CHOSEN);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("English\uD83C\uDDFA\uD83C\uDDF8")
                            && userStep.getStepName().equals(Steps.BOLIM)) {
                        userStep.setStepLang(Steps.ENGLISH);
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Kerakli bo'limni tanlang:");

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);

                        List<KeyboardRow> keyboardRows = new ArrayList<>();

                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        KeyboardRow keyboardRow2 = new KeyboardRow();
                        KeyboardRow keyboardRow3 = new KeyboardRow();
                        KeyboardRow keyboardRow4 = new KeyboardRow();
                        KeyboardButton keyboardButton1 = new KeyboardButton("registration for Eko-zakovat\uD83D\uDCDD");
                        KeyboardButton keyboardButton2 = new KeyboardButton("Information about Eko-zakovat\uD83D\uDCC4");
                        KeyboardButton keyboardButton3 = new KeyboardButton("About Oltin Qanot Volunteers\uD83C\uDF1E");
                        KeyboardButton keyboardButton4 = new KeyboardButton("Back to Languages' list↩️");

                        keyboardRow1.add(keyboardButton1);
                        keyboardRow2.add(keyboardButton2);
                        keyboardRow3.add(keyboardButton3);
                        keyboardRow4.add(keyboardButton4);

                        keyboardRows.add(keyboardRow1);
                        keyboardRows.add(keyboardRow2);
                        keyboardRows.add(keyboardRow3);
                        keyboardRows.add(keyboardRow4);

                        replyKeyboardMarkup.setKeyboard(keyboardRows);
                        message.setReplyMarkup(replyKeyboardMarkup);
                        userStep.setStepName(Steps.CHOSEN);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Русский\uD83C\uDDF7\uD83C\uDDFA")
                            && userStep.getStepName().equals(Steps.BOLIM)) {
                        userStep.setStepLang(Steps.RUSSIAN);
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Kerakli bo'limni tanlang:");

                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);

                        List<KeyboardRow> keyboardRows = new ArrayList<>();

                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        KeyboardRow keyboardRow2 = new KeyboardRow();
                        KeyboardRow keyboardRow3 = new KeyboardRow();
                        KeyboardRow keyboardRow4 = new KeyboardRow();
                        KeyboardButton keyboardButton1 = new KeyboardButton("Регистрация на Eko-zakovat\uD83D\uDCDD");
                        KeyboardButton keyboardButton2 = new KeyboardButton("Узнайте об Eko-zakovat\uD83D\uDCC4");
                        KeyboardButton keyboardButton3 = new KeyboardButton("О волонтерах Oltin Qanot\uD83C\uDF1E");
                        KeyboardButton keyboardButton4 = new KeyboardButton("Вернуться к списку языков↩️");

                        keyboardRow1.add(keyboardButton1);
                        keyboardRow2.add(keyboardButton2);
                        keyboardRow3.add(keyboardButton3);
                        keyboardRow4.add(keyboardButton4);

                        keyboardRows.add(keyboardRow1);
                        keyboardRows.add(keyboardRow2);
                        keyboardRows.add(keyboardRow3);
                        keyboardRows.add(keyboardRow4);

                        replyKeyboardMarkup.setKeyboard(keyboardRows);
                        message.setReplyMarkup(replyKeyboardMarkup);
                        userStep.setStepName(Steps.CHOSEN);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }




                    else if (text.equals("Eko-zakovatga ro'yhatdan o'tish\uD83D\uDCDD")
                            && !userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("F.I.O gizni kiriting:");
                        userStep.setStepName(Steps.ENTER_NAME);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Eko-zakovat haqida ma'lumot olish\uD83D\uDCC4")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            Ekologiya va zakovatga qiziqqanlar uchun ajoyib imkoniyat. Ya'ni
                            siz mantiqiy savollarni tinglash orqali oʻz boʻlingizni oshirib olasiz
                            va sertifikatlarga ega boʻlasiz. Eko zakovatga ishtirok eting🤗
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Oltin Qanot Volontiyorlari haqida\uD83C\uDF1E")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            OLTIN QANOT VOLONTYORI JAMASIGA HUSH KELIBSIZ!
                            Biz O'zbekistondagi eng faol, BIRINCHI raqamli va tashabbuskor VOLONTYORLAR jamoasimiz.
                            Ezgulik va yaxshilik uchun olg'a!
                            🌐 Bizning sayt www.volunteers.uz
                            👉http://myurls.co/volunteers_uz
                            📩 @Volunteers_uzbot
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }


                    else if (
                            text.equals("Регистрация на Eko-zakovat\uD83D\uDCDD")
                            && userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)
                            ||
                            text.equals("registration for Eko-zakovat\uD83D\uDCDD")
                            && userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)
                            ||
                            text.equals("Eko-zakovatga ro'yhatdan o'tish\uD83D\uDCDD")
                            && userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)
                    ) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        if (userStep.getStepLang().equals(Steps.UZBEK))
                            message.setText("Siz registratsiyadan o'tgansiz!");
                        if (userStep.getStepLang().equals(Steps.ENGLISH))
                            message.setText("You are registered!");
                        if (userStep.getStepLang().equals(Steps.RUSSIAN))
                            message.setText("Вы зарегистрированы!");
                        userStep.setStepName(Steps.CHOSEN);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }


                    else if (text.equals("registration for Eko-zakovat\uD83D\uDCDD")
                            && !userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Enter your Fullname:");
                        userStep.setStepName(Steps.ENTER_NAME);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    }
                    else if (text.equals("Information about Eko-zakovat\uD83D\uDCC4")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            Great opportunities for those interested in ecology and ingenuity. That is
                            you can improve your score by listening to logical questions
                            and you will get certificates. Participate in Eko zakovat🤗
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("About Oltin Qanot Volunteers\uD83C\uDF1E")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            WELCOME TO THE GOLD WING VOLUNTEER COMMUNITY!
                            We are the most active, the FIRST digital and initiative team of VOLUNTEERS in Uzbekistan.
                            For goodness sakes, go ahead!
                            🌐 Our website is www.volunteers.uz
                            👉http://myurls.co/volunteers_uz
                            📩 @Volunteers_uzbot
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }



                    else if (text.equals("Регистрация на Eko-zakovat\uD83D\uDCDD")
                            && !userStep.isRegistered()
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("Введите свое полное имя:");
                        userStep.setStepName(Steps.ENTER_NAME);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("Узнайте об Eko-zakovat\uD83D\uDCC4")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            Большие возможности для тех, кто интересуется экологией и изобретательностью. То есть
                            вы можете увеличить свой балл, слушая логические вопросы
                            и вы получите сертификаты. Примите участие в экоразведке🤗
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("О волонтерах Oltin Qanot\uD83C\uDF1E")
                            && userStep.getStepName().equals(Steps.CHOSEN)) {
                        SendMessage message = new SendMessage();
                        message.setChatId(userStep.getChatId());
                        message.setText("""
                            ДОБРО ПОЖАЛОВАТЬ В ВОЛОНТЕРСКОЕ СООБЩЕСТВО GOLD WING!
                            Мы самая активная, ПЕРВАЯ цифровая и инициативная команда ВОЛОНТЕРОВ в Узбекистане.
                            Ради бога, вперед!
                            🌐 Наш сайт www.volunteers.uz.
                            👉http://myurls.co/volunteers_uz
                            📩 @Volunteers_uzbot
                            """);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }



                    else if (userStep.getStepName().equals(Steps.ENTER_NAME)) {
                        System.out.println("name");
                        user.setFullName(text);
                        SendMessage sm = new SendMessage();
                        sm.setChatId(userStep.getChatId());
                        if (userStep.getStepLang().equals(Steps.UZBEK)) sm.setText("Raqamingizni kiriting:");
                        if (userStep.getStepLang().equals(Steps.ENGLISH)) sm.setText("Enter Your Number:");
                        if (userStep.getStepLang().equals(Steps.RUSSIAN)) sm.setText("Введите свой номер:");
                        userStep.setStepName(Steps.ENTER_NUMBER);
                        try {
                            execute(sm);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    }
                    else if (userStep.getStepName().equals(Steps.ENTER_NUMBER)) {
                        System.out.println("number");
                        user.setPhoneNumber(text);
                        SendMessage sm = new SendMessage();
                        sm.setChatId(userStep.getChatId());
                        if (userStep.getStepLang().equals(Steps.UZBEK))
                            sm.setText("Telegram usernamingizni kiriting(@username):");
                        if (userStep.getStepLang().equals(Steps.ENGLISH))
                            sm.setText("Enter your Telegram username(@username):");
                        if (userStep.getStepLang().equals(Steps.RUSSIAN))
                            sm.setText("Введите свое имя пользователя Telegram (@username):");
                        userStep.setStepName(Steps.ENTER_USERNAME);
                        try {
                            execute(sm);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (userStep.getStepName().equals(Steps.ENTER_USERNAME)) {
                        System.out.println("username");
                        user.setTgLink(text);
                        SendMessage sm = new SendMessage();
                        sm.setChatId(userStep.getChatId());
                        if (userStep.getStepLang().equals(Steps.UZBEK))
                            sm.setText(String.format("%s muvoffaqiyatli ro'yhatdan o'tdingiz!\n join group: https://t.me/+OSrvUDJlN3wyMmFi", user.getFullName()));
                        if (userStep.getStepLang().equals(Steps.RUSSIAN))
                            sm.setText(String.format("%s успешно зарегистрировался!\n join group: https://t.me/+OSrvUDJlN3wyMmFi", user.getFullName()));
                        if (userStep.getStepLang().equals(Steps.ENGLISH))
                            sm.setText(String.format("%s successfully registered!\n join group: https://t.me/+OSrvUDJlN3wyMmFi", user.getFullName()));

                        userStep.setStepName(Steps.CHOSEN);
                        userStep.setRegistered(true);

                        try {
                            execute(sm);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                            write(user);
                    }





                    else if (text.equals("Excel")
                            && !userStep.getStepName().equals(Steps.ENTER_NAME)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(userStep.getChatId());
                        sendMessage.setText("Parolni kiriting:");
                        userStep.setStepName(Steps.ENTER_PASSWORD);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (userStep.getStepName().equals(Steps.ENTER_PASSWORD)) {
                        if (text.equals("Oltinqanot")) {
                            SendDocument sendDocument = new SendDocument();
                            sendDocument.setDocument(new InputFile(new File("src/main/resources/Eko-zakovat.xlsx")));
                            sendDocument.setChatId(update.getMessage().getChatId());
                            userStep.setStepName(Steps.CHOSEN);
                            try {
                                execute(sendDocument);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            SendMessage sm = new SendMessage();
                            sm.setChatId(userStep.getChatId());
                            sm.setText("Uzur noto'g'ri parol❌");
                            userStep.setStepName(Steps.CHOSEN);
                            try {
                                execute(sm);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }
            }
        }
        else{
            sendChannelsID(update);
        }

    }

    private void sendChannelsID(Update update) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        // Create buttons
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Open Channel");
        button1.setUrl("https://t.me/" + CHANNEL_ID2.substring(1)); // Replace with your channel URL
        keyboardButtonsRow1.add(button1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Open Channel");
        button2.setUrl("https://t.me/" + CHANNEL_ID1.substring(1)); // Replace with your channel URL
        keyboardButtonsRow2.add(button2);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Check✅");
        button.setCallbackData("button_pressed"); // This will be used to identify the button press
        keyboardButtonsRow3.add(button);

        // Add buttons to the keyboard
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(keyboardButtonsRow1);
        rowsInline.add(keyboardButtonsRow2);
        rowsInline.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowsInline);


        sm.setReplyMarkup(inlineKeyboardMarkup);
        sm.setText("Botdan foydalanish uchun kanallarga azo bo'ling: ");
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        String callbackQueryId = update.getCallbackQuery().getId();

        if ("button_pressed".equals(callbackData)) {
            System.out.println("buttonga keldi");
            EditMessageText sm = new EditMessageText();
            sm.setMessageId((int) message_id);
            sm.setChatId(chatId);
            if (isUserMemberOfChannel(update.getCallbackQuery().getFrom().getId(), CHANNEL_ID1)
                    && isUserMemberOfChannel(update.getCallbackQuery().getFrom().getId(), CHANNEL_ID2)){

                System.out.println("ifda");
                sm.setText("Siz barcha kanalga a'zo bo'libsiz /start ni bosing!");
            }
            else {
                System.out.println("else tushdi");
                sm.setText("Barcha kanalga a'zo bo'ling");
            }
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.err.println("Failed to edit message or answer callback query: " + e.getMessage());
            }
            sendChannelsID(update);

            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(callbackQueryId);
            answer.setText("Button clicked");

            try {
                execute(answer); // Send the answer to the callback query
                System.out.println("Callback query answered successfully.");
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.err.println("Failed to answer callback query: " + e.getMessage());
            }
        }
    }

    private boolean isUserMemberOfChannel(long userId, String channelId) {
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId(channelId);
        getChatMember.setUserId(userId);

        try {
            ChatMember chatMember = execute(getChatMember);
            // Check if the user's status is "member", "administrator", or "creator"
            String status = chatMember.getStatus();
            return status.equals("member") || status.equals("administrator") || status.equals("creator");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }
    private UserStep saveUser(String chatId) {
        for (UserStep userStep : users) {
            if (userStep.getChatId().equals(chatId)) {
                return userStep;
            }
        }
        UserStep userStep1 = new UserStep();
        userStep1.setChatId(chatId);
        userStep1.setStepName(Steps.START);
        users.add(userStep1);
        return userStep1;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }
}

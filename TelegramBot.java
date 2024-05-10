package sch.bot.SchDemoBot.service;

/* 7075103472:AAEEKhRE0bPwNddBONL8CABtC-y9pOpVMlw FAQprojectbot */

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sch.bot.SchDemoBot.config.BotConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    static final String ANS = "Ответили пользователю ";
    static final String ERROR_TEXT = "Произошла ошибка ";
    static final String QUESTION_TEXT = "Вы нашли то, что искали?";
    static final String THANKS_TEXT = EmojiParser.parseToUnicode("Был рад помочь! " + ":space_invader:");
    static final String TO_MENU_TEXT = EmojiParser.parseToUnicode("Если вы хотите вернуться"
            + " в меню, то нажмите кнопку ниже " + ":arrow_down:");
    /*static final String COMM_THANKS_TEXT = EmojiParser.parseToUnicode("Был рад помочь!\n\nНажмите " + ":clipboard:"
            + " /menu, чтобы вернуться в меню.\n\nНажмите " + ":email:" + " /help, чтобы получить " +
            "контакты для обратной связи");*/
    static final String YES_BUTTON = "YES_BUTTON";
    static final String YES_BUTTON_TEXT = EmojiParser.parseToUnicode(":white_check_mark:" + " Да");
    static final String NO_BUTTON = "NO_BUTTON";
    static final String NO_BUTTON_TEXT = EmojiParser.parseToUnicode(":x:" +" Нет");
    static final String FAQ_BUTTON = "FAQ_BUTTON";
    static final String FAQ_BUTTON_TEXT = EmojiParser.parseToUnicode(":question:" + " FAQ");
    static final String HELP_BUTTON_MENU = "HELP_BUTTON_MENU";
    static final String HELP_BUTTON_MENU_TEXT = EmojiParser.parseToUnicode(":email:" + " Поддержка");
    static final String TO_FAQ_BUTTON = "TO_FAQ_BUTTON";
    static final String TIME_BUTTON = "TIME_BUTTON";
    static final String TIME_BUTTON_TEXT = EmojiParser.parseToUnicode(":clock12:" + " График работы");
    static final String LOCATION_BUTTON = "LOCATION_BUTTON";
    static final String LOCATION_BUTTON_TEXT = EmojiParser.parseToUnicode(":house:" + " Адреса офисов");
    static final String LOYALTY_BUTTON = "LOYALTY_BUTTON";
    static final String LOYALTY_BUTTON_TEXT = EmojiParser.parseToUnicode(":credit_card:" + " Программа лояльности");
    static final String JOB_BUTTON = "JOB_BUTTON";
    static final String JOB_BUTTON_TEXT = EmojiParser.parseToUnicode(":wrench:" + " Трудоустройство");
    static final String HELP_BUTTON = "HELP_BUTTON";
    static final String HELP_BUTTON_USED = EmojiParser.parseToUnicode(":email:" + " Поддержка");
    static final String NO_BUTTON_USED = "Нажата кнопка \"Нет\"";
    static final String FAQ_BUTTON_USED = EmojiParser.parseToUnicode("Нажата кнопка \"" + ":question:"
            + " FAQ\"");
    static final String TO_MENU_BUTTON = "TO_MENU_BUTTON";
    static final String TO_MENU_BUTTON_TEXT = EmojiParser.parseToUnicode(":clipboard:" + " Меню");
    static final String TO_MENU_BUTTON_USED = EmojiParser.parseToUnicode("Нажата кнопка \"" + ":clipboard:"
            + " Меню\"");
    /*static final String HELP_BUTTON_USED = EmojiParser.parseToUnicode("Нажата кнопка \"" + ":email:"
            + " Поддержка\"");
    static final String MENU_TEXT = EmojiParser.parseToUnicode("Используйте команды из перечня ниже или из " +
            "меню (в левом нижнем углу), чтобы общаться с ботом. " + ":space_invader:" + "\n\n" +
            ":question:" + " /faq - ответы на часто задаваемые вопросы\n\n" +
            //":clipboard:" + " /menu - меню бота\n\n" +
            //":rocket:" + " /start - перезапуск\n\n" +
            ":email:" + " /help - контакты поддержки\n\n\n*FAQ - frequently asked questions, " +
            "часто задаваемые вопросы " + ":grey_question:");*/
    static final String START_MENU_COMEBACK_TEXT = EmojiParser.parseToUnicode("Используйте кнопки из списка ниже "
            + ":arrow_down:");
    static final String QUESTION_TEXT_FAQ = EmojiParser.parseToUnicode("Oтвет на какой вопрос вас интересует?");
    //static final String WHAT_IS_FAQ_TEXT = "FAQ - часто задаваемые вопросы\n(frequently asked questions)";
    static final String NO_FAQ_TEXT = "Нажмите \"FAQ\"";

    /*static final String FAQ_TEXT = ", ответ на какой вопрос вас интересует? " + ":space_invader:" +
            "\n\n" + ":clock12:" + " /time - по какому графику работают отделения?\n\n" +
            ":house:" + " /location - где находятся офисы?\n\n" +
            ":credit_card:" + " /loyalty - как работает программа лояльности?\n\n" +
            ":wrench:" + " /job - как устроиться к вам на работу?\n\n" +
            ":email:" + " /help - как связаться с поддержкой? (активна по графику работы)\n\n";*/

    static final String TIME_TEXT = EmojiParser.parseToUnicode( ":alarm_clock:" + " График работы:\n\n" +
            "Пн-Пт:   9:00-18:00\n    Сб:      10:00-17:00\n    Вс:       Выходной\n\nПерерыв на обед: 13:00-14:00");
    static final String LOCATION_TEXT = EmojiParser.parseToUnicode(":briefcase:" + " Офисы:\n\nЦентральный " +
            "офис:\nАдрес: 152934б, г.Рыбинск, ул.Радищева, д.15" + "\nТелефон: +7(4855)21-79-32\n\nОтделение:\n" +
            "Адрес: 152934, г.Рыбинск, ул.Крестовая, д.92" + "\nТелефон: +7(4855)21-79-32");
    static final String LOYALTY_TEXT = EmojiParser.parseToUnicode(":gift:" + " Программа лояльности:\n\n" +
            "    Для клиентов, не участвующих в программе лояльности, отличным приветственным бонусом будет " +
            "единоразовая скидка на услугу в размере 10% при вступлении в программу.\n\n    При участии " +
            "клиента в программе лояльности сроком менее года, клиент получает привилегии в виде постоянной " +
            "скидки на услуги в размере 15%.\n\n    Клиенты, участвующие в программе лояльности на протяжении " +
            "довольно длительного срока - года и более, имеют право на постоянную скидку в размере 20% и права " +
            "на вечное бесплатное обслуживание.");
    static final String JOB_TEXT = EmojiParser.parseToUnicode(":loudspeaker:" + " Трудоустройство, вакансии:" +
            "\n\nТребуются кадры по следующим вакансиям:\n- " + ":memo:" + " бухгалтер\n- " + ":speech_balloon:" +
            " менеджер.\n\nПо вакансии бухгалтера " + ":memo:" + " :\nТребования:\n- опыт работы не менее 2 лет\n" +
            "- высшее экономическое образование\n- знание законодательства в области бухучета и налогов\n- " +
            "уверенное владение специализированными бухгалтерскими программами\n- внимательность, пунктуальность, " +
            "ответственность\n\nВ обязанности входит все, что связано с ведением бухгалтерского учета.\n\n\nПо " +
            "вакансии менеджера "+ ":speech_balloon:" + " :\nТребования:\n- опыт работы не менее 3 лет\n- высшее " +
            "образование в сфере менеджмента\n- кандидат на вакансию должен быть коммуникабельным, уметь вести " +
            "переговоры и логически мыслить, разбираться в психологии, финансах и делопроизводстве\n\nГлавная " +
            "обязанность менеджера — уметь объединять подчиненных, грамотно ставить задачи и работать в команде." +
            "\n\n\nПодробнее по контактному номеру телефона центрального офиса \n" + ":pushpin:" + " /location\n" +
            "или свяжитесь с поддержкой\n" + ":email:" + " /help\n\nМы ждем имено Ваше заявление о " +
            "приеме на работу!");
    static final String HELP_TEXT = EmojiParser.parseToUnicode("Для связи с поддержкой писать:\n\nTelegram - " +
            "@yeatest " + ":space_invader:" + "\nEmail - antony01s@mail.ru " + ":space_invader:" + /*"\n\nНажмите " +
            ":clipboard:" + " /menu, чтобы вернуться обратно" +*/ "\n\n" + ":rocket:" + " /start - перезапуск\n\n");
    static final String COMMAND_NOT_RECOGNISED_TEXT = ", такой команды нет " + ":cry:" +
            "\n\nОткройте \"Меню\" " + ":clipboard:" + " слева снизу или выберите команду из списка - /menu";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Начать"));
        //listOfCommands.add(new BotCommand("/faq", "Ответы на часто задаваемые вопросы"));
        //listOfCommands.add(new BotCommand("/menu", "Меню"));
        listOfCommands.add(new BotCommand("/help", "Поддержка"));
        //listOfCommands.add(new BotCommand("/time", "График"));
        //listOfCommands.add(new BotCommand("/location", "Адреса"));
        //listOfCommands.add(new BotCommand("/loyalty", "Программа лояльности"));
        //listOfCommands.add(new BotCommand("/job", "Трудоустройство"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),
                    null));
        } catch (TelegramApiException e) {
            log.error("Ошибка при настройке списка команд бота: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {

                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                /*case "/faq":
                    faqCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/menu":
                    sendMessage(chatId, MENU_TEXT);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (menu)");
                    break;*/

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    comeback(chatId);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (HELP)");
                    break;

                /*case "/time":
                    sendMessage(chatId, TIME_TEXT);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (time)");
                    comeback(chatId);
                    break;*/

                case "/location":
                    sendMessage(chatId, LOCATION_TEXT);
                    comeback(chatId);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (LOCATION)");
                    break;

                /*case "/loyalty":
                    sendMessage(chatId, LOYALTY_TEXT);
                    comeback(chatId);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (loyalty)");
                    break;

                case "/job":
                    sendMessage(chatId, JOB_TEXT);
                    comeback(chatId);
                    sendLogIfCorrect(chatId, update.getMessage().getChat().getFirstName(), " (job)");
                    break;*/
                default:
                    commandNotRecognised(chatId, update.getMessage().getChat().getFirstName());
            }
        }else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String name = update.getCallbackQuery().getMessage().getChat().getFirstName();

            if(callbackData.equals(YES_BUTTON)){
                String text = THANKS_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comebackYes(chatId);
                sendLogIfCorrect(chatId, name, " (YES_BUTTON)");

            }else if(callbackData.equals(NO_BUTTON)){
                String text = NO_BUTTON_USED;
                executeEditMessageText(text, chatId, messageId);
                comebackFaq(chatId);
                sendLogIfCorrect(chatId, name, " (NO_BUTTON)");

            }else if(callbackData.equals(TIME_BUTTON)){
                String text = TIME_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comeback(chatId);
                sendLogIfCorrect(chatId, name, " (TIME_BUTTON)");

            }else if(callbackData.equals(LOCATION_BUTTON)){
                String text = LOCATION_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comeback(chatId);
                sendLogIfCorrect(chatId, name, " (LOCATION_BUTTON)");

            }else if(callbackData.equals(LOYALTY_BUTTON)){
                String text = LOYALTY_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comeback(chatId);
                sendLogIfCorrect(chatId, name, " (LOYALTY_BUTTON)");

            }else if(callbackData.equals(JOB_BUTTON)){
                String text = JOB_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comeback(chatId);
                sendLogIfCorrect(chatId, name," (JOB_BUTTON)");

            }else if(callbackData.equals(HELP_BUTTON)) {
                String text = HELP_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comeback(chatId);
                sendLogIfCorrect(chatId, name, " (HELP_BUTTON)");

            }else if(callbackData.equals(FAQ_BUTTON)) {
                String text = FAQ_BUTTON_USED;
                executeEditMessageText(text, chatId, messageId);
                comebackFaq(chatId);
                sendLogIfCorrect(chatId, name, " (FAQ_BUTTON)");

            }else if(callbackData.equals(HELP_BUTTON_MENU)) {
                String text = HELP_TEXT;
                comeback(chatId);
                executeEditMessageText(text, chatId, messageId);
                sendLogIfCorrect(chatId, name, " (HELP_BUTTON_MENU)");

            }else if(callbackData.equals(TO_MENU_BUTTON)) {
                String text = TO_MENU_BUTTON_USED;
                comebackMenu(chatId);
                executeEditMessageText(text, chatId, messageId);
                sendLogIfCorrect(chatId, name, " (TO_MENU_BUTTON)");

            }if(callbackData.equals(TO_FAQ_BUTTON)) {
                String text = NO_FAQ_TEXT;
                executeEditMessageText(text, chatId, messageId);
                comebackFaq(chatId);
                sendLogIfCorrect(chatId, name, " (TO_FAQ_BUTTON)");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Приветствую, " + name + "! " + ":space_invader:" + "\nЯ " +
                "помогу найти ответ на интересующий вас вопрос."
                /*+ "\n\n"+ ":clipboard:" + " /menu - открыть меню бота"*/);

        sendMessage(chatId, answer);

        comebackMenu(chatId);

        log.info(ANS + name + " (START)");
    }

    private void faqCommandReceived(long chatId, String name){
        //String answer = EmojiParser.parseToUnicode(name + FAQ_TEXT);

        comebackFaq(chatId);

        //sendMessage(chatId, answer);

        log.info(ANS + name + " (FAQ)");
    }

    private void commandNotRecognised(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode(name + COMMAND_NOT_RECOGNISED_TEXT);

        log.info(ANS + name + " об ошибке");
        sendMessage(chatId, answer);
    }

    private void sendLogIfCorrect(long chatId, String name, String command) {

        log.info(ANS + name + command);
    }

    private void comeback(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(QUESTION_TEXT);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var yesButton = new InlineKeyboardButton();

        yesButton.setText(YES_BUTTON_TEXT);
        yesButton.setCallbackData(YES_BUTTON);


        var noButton = new InlineKeyboardButton();

        noButton.setText(NO_BUTTON_TEXT);
        noButton.setCallbackData(NO_BUTTON);


        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message) ;
}

    private void comebackFaq(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(QUESTION_TEXT_FAQ);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine5 = new ArrayList<>();

        var timeButton = new InlineKeyboardButton();

        timeButton.setText(TIME_BUTTON_TEXT);
        timeButton.setCallbackData(TIME_BUTTON);

        var locationButton = new InlineKeyboardButton();

        locationButton.setText(LOCATION_BUTTON_TEXT);
        locationButton.setCallbackData(LOCATION_BUTTON);

        var loyaltyButton = new InlineKeyboardButton();

        loyaltyButton.setText(LOYALTY_BUTTON_TEXT);
        loyaltyButton.setCallbackData(LOYALTY_BUTTON);

        var jobButton = new InlineKeyboardButton();

        jobButton.setText(JOB_BUTTON_TEXT);
        jobButton.setCallbackData(JOB_BUTTON);

        var helpButton = new InlineKeyboardButton();

        helpButton.setText(HELP_BUTTON_USED);
        helpButton.setCallbackData(HELP_BUTTON);


        rowInLine1.add(timeButton);
        rowInLine2.add(locationButton);
        rowInLine3.add(loyaltyButton);
        rowInLine4.add(jobButton);
        rowInLine5.add(helpButton);

        rowsInLine1.add(rowInLine1);
        rowsInLine1.add(rowInLine2);
        rowsInLine1.add(rowInLine3);
        rowsInLine1.add(rowInLine4);
        rowsInLine1.add(rowInLine5);

        markupInLine.setKeyboard(rowsInLine1);
        message.setReplyMarkup(markupInLine);

        executeMessage(message) ;
    }


    private void comebackYes(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(TO_MENU_TEXT);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var toMenuButton = new InlineKeyboardButton();

        toMenuButton.setText(TO_MENU_BUTTON_TEXT);
        toMenuButton.setCallbackData(TO_MENU_BUTTON);


        rowInLine.add(toMenuButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message) ;
    }
    private void comebackMenu(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(START_MENU_COMEBACK_TEXT);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var faqButton = new InlineKeyboardButton();

        faqButton.setText(FAQ_BUTTON_TEXT);
        faqButton.setCallbackData(FAQ_BUTTON);


        var helpButtonMenu = new InlineKeyboardButton();

        helpButtonMenu.setText(HELP_BUTTON_MENU_TEXT);
        helpButtonMenu.setCallbackData(HELP_BUTTON_MENU);


        rowInLine.add(faqButton);
        rowInLine.add(helpButtonMenu);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message) ;
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        executeMessage(message);
    }

    private void executeEditMessageText(String text, long chatId, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void executeMessage(SendMessage message){

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }


    //лишний шаг получился
    /*private void comebackNO(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(NO_FAQ_TEXT);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var toFaqButton = new InlineKeyboardButton();

        toFaqButton.setText(EmojiParser.parseToUnicode(":question:" + " FAQ"));
        toFaqButton.setCallbackData(TO_FAQ_BUTTON);


        rowInLine.add(toFaqButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message) ;
    }*/



    /*private void sendPhoto (long chatId, String localPath) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new java.io.File(localPath));
        photo.setPhoto(inputFile);
        try{
            execute(photo);
        }
        catch (TelegramApiException e){
            log.error("Произошла ошибка " + e.getMessage());
        }
    }*/
}
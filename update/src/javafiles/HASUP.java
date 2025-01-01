package javafiles;

import java.util.ArrayList;
import java.util.List;

public class HASUP {
    private final List<String> users = new ArrayList<>();

    public synchronized String processMessage(String message) {
        // Gelen mesajdaki boşlukları temizle
        message = message.trim();

        if (message.startsWith("KAYIT:")) {
            String userInfo = message.substring(6).trim();
            users.add(userInfo);
            return "Kullanıcı başarıyla kaydedildi: " + userInfo;
        } else if (message.equalsIgnoreCase("GORUNTULE")) {
            return "Kayıtlı kullanıcılar: " + String.join(", ", users);
        } else {
            return "Geçersiz komut.";
        }
    }
}

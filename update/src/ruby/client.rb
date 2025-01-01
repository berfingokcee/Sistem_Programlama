require 'socket'
require 'thread'

users = []
mutex = Mutex.new

def process_message(message, users, mutex)
  # Mesajı zorla UTF-8 formatına dönüştür
  message = message.encode('UTF-8', invalid: :replace, undef: :replace, replace: '?') if message.encoding.name != 'UTF-8'
  puts "Mesaj alindi: #{message}"  # Hata ayıklama çıktısı ekledik

  if message.start_with?("KAYIT:")
    user_info = message[6..].strip
    mutex.synchronize { users << user_info }
    return "Kullanici basariyla kaydedildi: #{user_info}".encode('UTF-8')
  elsif message.strip == "GORUNTULE"
    response = "Kayitli kullanicilar: #{users.join(", ")}"
    return response.encode('UTF-8')  # Cevapları UTF-8 olarak döndürüyoruz
  else
    return "Gecersiz komut.".encode('UTF-8')
  end
end

server = TCPServer.new('localhost', 8081)
puts "Sunucu 8081 portunda çalışıyor..."

loop do
  Thread.start(server.accept) do |client|
    puts "Yeni bağlantı kabul edildi."  # Yeni bağlantı çıktısı ekledik
    loop do
      message = client.gets&.chomp
      break unless message
      # Mesajı zorla UTF-8 formatına dönüştür
      message = message.encode('UTF-8', invalid: :replace, undef: :replace, replace: '?') if message.encoding.name != 'UTF-8'
      puts "Mesaj alındı: #{message}"  # Alınan mesajı yazdırıyoruz
      response = process_message(message, users, mutex)
      puts "Cevap gönderiliyor: #{response}"  # Cevabı yazdırıyoruz
      client.puts(response)
    end
    client.close
  end
end

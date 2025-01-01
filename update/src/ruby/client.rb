require 'socket'

socket = TCPSocket.new('localhost', 8081)
puts "Bağlantı kuruldu. Komutlarınızı yazın:"

loop do
  print "> "
  message = gets.chomp
  # Mesajı zorla UTF-8 formatına dönüştür
  message = message.encode('UTF-8', invalid: :replace, undef: :replace, replace: '?') if message.encoding.name != 'UTF-8'
  socket.puts message
  response = socket.gets
  # Yanıtı zorla UTF-8 formatına dönüştür
  response = response.encode('UTF-8', invalid: :replace, undef: :replace, replace: '?') if response.encoding.name != 'UTF-8'
  puts response
end

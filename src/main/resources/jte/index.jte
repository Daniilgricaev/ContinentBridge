@param java.util.Queue<String> messages
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Простой чат</title>
<style>
  * { box-sizing: border-box; margin: 0; padding: 0; }
  body {
    font-family: Arial, sans-serif;
    background: #f0f2f5;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
  }
  .chat {
    width: 100%;
    max-width: 480px;
    height: 600px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0,0,0,0.1);
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }
  .chat-header {
    background: #4a76a8;
    color: #fff;
    padding: 15px 20px;
    font-size: 18px;
    font-weight: bold;
  }
  .messages {
    flex: 1;
    padding: 15px;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 10px;
    background: #e5ddd5;
  }
  .msg {
    max-width: 75%;
    padding: 8px 12px;
    border-radius: 10px;
    font-size: 14px;
    line-height: 1.4;
    word-wrap: break-word;
    animation: appear 0.2s ease;
  }
  @keyframes appear {
    from { opacity: 0; transform: translateY(5px); }
    to { opacity: 1; transform: translateY(0); }
  }
  .msg.me {
    align-self: flex-end;
    background: #dcf8c6;
    border-bottom-right-radius: 2px;
  }
  .msg.other {
    align-self: flex-start;
    background: #fff;
    border-bottom-left-radius: 2px;
  }
  .msg .time {
    display: block;
    font-size: 11px;
    color: #888;
    text-align: right;
    margin-top: 4px;
  }
  .input-area {
    display: flex;
    padding: 10px;
    background: #f0f0f0;
    border-top: 1px solid #ddd;
  }
  .input-area input {
    flex: 1;
    padding: 10px 14px;
    border: 1px solid #ccc;
    border-radius: 20px;
    outline: none;
    font-size: 14px;
  }
  .input-area input:focus {
    border-color: #4a76a8;
  }
  .input-area button {
    margin-left: 8px;
    padding: 10px 18px;
    background: #4a76a8;
    color: #fff;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    font-size: 14px;
  }
  .input-area button:hover {
    background: #3b5f87;
  }
</style>
</head>
<body>

<div class="chat">
  <div class="chat-header">Chat</div>
  <div class="messages" id="messages">
    @for(var m : messages)
        <div class="msg other">${m}</div>
    @endfor
  </div>
  <div class="input-area">
    <input type="text" id="input" placeholder="Enter message..." autocomplete="off">
    <button onclick="sendMessage()">Send</button>
  </div>
</div>

<script>
  const messages = document.getElementById('messages');
  const input = document.getElementById('input');
  function sendMessage() {
    const text = input.value.trim();
    if (!text) return;
    const now = new Date();
    const time = now.getHours().toString().padStart(2, '0') + ':' +
                 now.getMinutes().toString().padStart(2, '0');

    const msg = document.createElement('div');
    msg.className = 'msg me';
    msg.innerHTML = escapeHtml(text) + '<span class="time">' + time + '</span>';
    messages.appendChild(msg);

    input.value = '';
    messages.scrollTop = messages.scrollHeight;
    fetch('/send', {
        method: 'POST',
        headers: {'Content-Type': 'text/plain; charset=UTF-8'},
        body: 'message=' + text
    });
  }

  input.addEventListener('keydown', e => {
    if (e.key === 'Enter') sendMessage();
  });

  function escapeHtml(str) {
    return str.replace(/[&<>"']/g, c => ({
      '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    })[c]);
  }
</script>

</body>
</html>
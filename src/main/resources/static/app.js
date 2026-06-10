
var stompClient = null;

// ── UI Elements ────────────────────────────────────────────────────────────
var messageFeed      = document.getElementById('feed');
var messageInput     = document.getElementById('msgInput');
var sendButton       = document.getElementById('sendBtn');
var connectButton    = document.getElementById('connectBtn');
var disconnectButton = document.getElementById('disconnectBtn');
var statusLabel      = document.getElementById('status');

// ── Helper: Add a message bubble to the chat feed ──────────────────────────
function showMessageInFeed(text) {
  var messageDiv = document.createElement('div');
  messageDiv.className = 'msg';
  messageDiv.textContent = text;
  messageFeed.appendChild(messageDiv);
  messageFeed.scrollTop = messageFeed.scrollHeight;
}

// ── Helper: Enable/disable UI controls ─────────────────────────────────────
function setConnectedState(isConnected) {
  connectButton.disabled    =  isConnected;
  disconnectButton.disabled = !isConnected;
  messageInput.disabled     = !isConnected;
  sendButton.disabled       = !isConnected;
}

// ── Connect Button ──────────────────────────────────────────────────────────
connectButton.onclick = function () {

  stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws',
    debug: function (msg) { console.log('[STOMP]', msg); }
  });

  stompClient.onConnect = function () {
    statusLabel.textContent = 'Connected';
    setConnectedState(true);

    stompClient.subscribe('/topic/messages', function (frame) {
      var receivedData = JSON.parse(frame.body);
      showMessageInFeed(receivedData.chat);
    });
  };

  stompClient.onDisconnect = function () {
    statusLabel.textContent = 'Disconnected';
    setConnectedState(false);
  };

  // Handle connection errors
  stompClient.onStompError = function (frame) {
    statusLabel.textContent = 'Error: ' + frame.headers['message'];
    console.error('STOMP error', frame);
  };

  stompClient.activate();
};

// ── Disconnect Button ───────────────────────────────────────────────────────
disconnectButton.onclick = function () {
  stompClient.deactivate();
};

// ── Send Button ─────────────────────────────────────────────────────────────
sendButton.onclick = function () {
  var text = messageInput.value.trim();
  if (!text) return;

  stompClient.publish({
    destination: '/app/hello',
    body: JSON.stringify({ chat: text })
  });

  messageInput.value = '';
};

// ── Enter key = Send ────────────────────────────────────────────────────────
messageInput.onkeydown = function (event) {
  if (event.key === 'Enter') sendButton.onclick();
};
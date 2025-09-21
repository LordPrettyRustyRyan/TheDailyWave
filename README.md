# The Daily Wave

A modern Android news app that fetches live headlines using the [NewsAPI](https://newsapi.org/), with our own Node/Express proxy server to handle API requests securely.
<img width="3265" height="2000" alt="pic" src="https://github.com/user-attachments/assets/b41e5185-2b80-41ae-8358-9444cd3d9571" />

---

## Features
- Browse **top headlines** by category (General, Business, Sports, Technology, Health, Entertainment, Science).
- **Search bar** to find specific news articles.
- Click an article to open a **full web view** inside the app.
- Loading state with **progress indicator**.
- Works with a **custom Node.js/Express proxy server** (for secure API key usage).
- Custom app icon & modern UI.

---

## Backend Setup (Node/Express Proxy)

We use a small proxy server so the API key is **not exposed inside the Android app**.

1. Go into the backend folder:
   ```bash
   cd newsapi-proxy

2. Install dependencies:
   ```bash
   npm install express axios cors dotenv

3. Create a .env file:
   ```bash
   NEWS_API_KEY=your_newsapi_key_here

4. Run the server:
   ```bash
   node server.js

By default it runs at:
http://localhost:5000/news

## Example Requests
Get general headlines:
http://localhost:5000/news?category=general

Get technology news:
http://localhost:5000/news?category=technology

Search for a keyword:
http://localhost:5000/news?q=bitcoin

## Android App Setup
1. Open the project in Android Studio.
2. Ensure your proxy server is running.
3. In MainActivity.java, update the server URL:

HttpUrl.Builder urlBuilder = HttpUrl.parse("http://10.0.2.2:5000/news").newBuilder();
- On emulator → use 10.0.2.2 (special alias to host machine).
- On real device (same WiFi) → replace with your PC’s IP, e.g. http://192.168.1.5:5000/news.
- For global usage → deploy the server (Render, Railway, etc.) and use the public URL.

4. Run the app → browse headlines by category, search, and view full articles.

## Common Issues & Fixes

- CLEARTEXT not permitted → add android:usesCleartextTraffic="true" in AndroidManifest.xml for local dev.
- Category buttons not working → ensure btn.setOnClickListener(this) is set for all buttons, and category text is converted to lowercase.
- Real device can’t fetch news → change URL from 10.0.2.2 → your PC’s local IP or deploy server online.

## Key Features

- RecyclerView API integration for smooth news list display
- Interactive buttons for easy news category filtering
- News API connectivity using NewsApiClient library
- Real-time news fetching and dynamic search functionality
- Elegant loading progress indicator for enhanced UX

## Roadmap

- Add pagination for endless scrolling.
- Save favorite articles locally.
- Dark mode UI.
- Push notifications for breaking news.

## License

MIT License.
This project uses NewsAPI.org (https://newsapi.org/) for educational purposes.
Do you want me to also add a **step-by-step guide for deploying the Node server to Render** inside this README so your APK works anywhere, not just on your WiFi?

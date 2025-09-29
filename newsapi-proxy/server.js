// server.js
import express from "express";
import axios from "axios";
import cors from "cors";
import dotenv from "dotenv";

dotenv.config(); // load .env

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());

// Proxy endpoint
app.get("/news", async (req, res) => {
  try {
    const { category, q } = req.query; // extract query params

    const params = {
      country: "us",
      apiKey: process.env.NEWS_API_KEY,
    };

    if (category) params.category = category.toLowerCase();
    if (q) params.q = q;

    const response = await axios.get("https://newsapi.org/v2/top-headlines", {
      params,
    });

    res.json(response.data);
  } catch (error) {
    console.error("❌ NewsAPI Error:", error.response?.data || error.message);
    res.status(500).json({
      status: "error",
      message: "Failed to fetch news",
      details: error.response?.data || error.message,
    });
  }
});

app.listen(PORT, () => {
  console.log(`✅ Proxy running at http://localhost:${PORT}/news`);
});

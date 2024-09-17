curl -X POST https://<your-ai-service-endpoint>/text/analytics/v3.1/languages \
-H "Content-Type: application/json" \
-H "Ocp-Apim-Subscription-Key: <your-ai-service-key>" \
-d '{
  "documents": [
    {
      "id": "1",
      "text": "<your-text>"
    }
  ]
}'

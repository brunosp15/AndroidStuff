
# Data to send inside the push
 data='{
  "title":"Title",
  "message": "Message",
  "type": 12,
  "receipt": "Teste",
  "subtitle": "Subtitle",
  "uniqueid": "3-60-1-1"
}';

# Key for the project
apiKey="API_KEY";


deviceId="DEVICE_ID";

# Make the resquest
curl -X POST \
-H "Authorization: key= $apiKey" \
-H "Content-Type: application/json" \
-d "{
\"registration_ids\": [
\"$deviceId\"
],
\"data\": $data,
\"priority\": \"high\"
}" \
https://gcm-http.googleapis.com/gcm/send

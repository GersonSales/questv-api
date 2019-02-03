import requests 


seriesData = [

{
    "name": "Death Note",
    "abbreviation": "Death Note",
    "category": "Anime"
}
]


postSeriesURL = "http://localhost:5000/series"
responseList = []

for serie in seriesData:
    print "Sending: " + str(serie)
    responseList.append(requests.post(url = postSeriesURL, data = serie).text)
    
    
for response in responseList:
    print response    









  
# api-endpoint 

  
# location given here 
  
# sending get request and saving the response as response object 
#r = requests.get(url = URL, params = PARAMS) 

 

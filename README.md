# iamalive
Service for URL availability monitoring

How to use:

**/schedule/status**  
Request type: GET  
Params: none  
Returns: current schedule with list of urls, their status and current delay, for example:
```
{
    "urls": {
        "http://www.google.ru": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/schedule/add_url**  
Request type: POST  
Params: json body with array of urls, for example: 
```
{
  "urls": [
    "http://www.yandex.ru",
    "http://www.google.ru"
  ]
}
```
Returns: current schedule with list of urls, their status and current delay, for example:
```
{
    "urls": {
        "http://www.google.ru": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```
**/schedule/delete_url**  
Request type: POST  
Params: json body with array of urls, for example:   
```
{
  "urls": [
    "http://www.yandex.ru",
    "http://www.google.ru"
  ]
}
```
Returns: current schedule with list of urls, their status and current delay, for example:
```
{
    "urls": {
        "http://www.google.ru": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/set_delay/{delay}**  
Request type: GET  
Params: new delay in seconds, for example: /set_delay/60  
Returns: current schedule with list of urls, their status and current delay, for example:  
```
{
    "urls": {
        "http://www.google.ru": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```



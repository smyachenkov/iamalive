# iamalive
Service for URL availability monitoring

## How to build:
It's a maven project
```
mvn clean install
```

## How to use:

**/schedule/status**  
Retrieves current status   
Request type: GET  
Params: none  
Returns: current schedule with list of urls, their status and current delay, for example:
```
{
    "urls": {
        "http://www.google.com": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/schedule/add_url**  
Adds new url or list of urls to current schedule  
Request type: POST  
Params: json object with present element "urls" - array of urls, for example:   
```
{
  "urls": [
    "http://www.google.com",
    "http://www.yandex.ru"
  ]
}
```
Params: json object with present element "urls" - array of urls, for example:
```
{
    "urls": {
        "http://www.google.com": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/schedule/delete_url**  
Removes url or list of urls from current schedule  
Request type: POST  
Params: json object with present element "urls" - array of urls, for example:   
```
{
  "urls": [
    "http://www.google.com",
    "http://www.yandex.ru"
  ]
}
```
Returns: current schedule with list of urls, their status and current delay, for example:
```
{
    "urls": {
        "http://www.google.com": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/schedule/set_delay**  
Updates refresh delay of current schedule  
Request type: POST  
Params: json object with present element "delay" - value of delay in seconds, for example:
```
{
    "delay": 5
}
```
Returns: current schedule with list of urls, their status and current delay, for example:  
```
{
    "urls": {
        "http://www.google.com": "AVAILABLE",
        "http://www.yandex.ru": "AVAILABLE"
    },
    "delay": 5
}
```


**/schedule/set_schedule**  
Replaces current schedule with new  
Request type: POST  
Params: Schedule json object with all field present, for example:
```
{
    "urls": {
        "http://www.google.com",
        "http://www.yandex.ru"
    },
    "delay": 5
}
```
Returns: current schedule with list of urls, their status and current delay, for example:  
```
{
    "urls": {
        "http://www.google.com": "NOT_CHECKED",
        "http://www.yandex.ru": "NOT_CHECKED"
    },
    "delay": 5
}
```




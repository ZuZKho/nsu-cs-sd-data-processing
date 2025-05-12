# Примеры использования API
https://app.swaggerhub.com/apis/MatveyZ/Aviasales/1.0.0
## Cities
```
http://localhost:8080/cities
http://localhost:8080/cities?lang=RU
http://localhost:8080/cities?lang=EN
```

## Airports
```
http://localhost:8080/airports?lang=RU
...
```

## City Airports
```
http://localhost:8080/airports/Новосибирск
http://localhost:8080/airports/Moscow?lang=RU
...
```

## Departures & Arrivals
```
http://localhost:8080/arrivals/KHV?fromTime=18:00&toTime=19:00&flightNumber=PG0651&daysOfWeek=MONDAY,FRIDAY
```

## Routes
```
http://localhost:8080/routes?source=LED&destination=UUS&departureDate=2016-12-11
http://localhost:8080/routes?source=OVB&destination=%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0&departureDate=2025-05-13&maxConnections=2
http://localhost:8080/routes?source=Moscow&destination=LED&departureDate=2016-12-11&maxConnections=2
```

## Booking
**POST to http://localhost:8080/booking/create** with body
```json
{
    "fareConditions": "Business",
    "flightIds": [398036, 398034],
    "passengerId": "9999 377533"
}
```
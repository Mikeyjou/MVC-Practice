#coding=utf-8 
import requests
import os
import threading


def book(num, payload):
    for j in range(3):
        r = requests.post("http://localhost:8080/WebApi/User/order", data=payload)
        print(r.text)
        
payload = [{'no': '112', 'account': 'mikey', 'password': '123', 'identifier': '8663aa6ec3ca4b7d94d15d0487104c12'},
           {'no': '112', 'account': 'mikey2', 'password': '123', 'identifier': 'e9c80ef11527434685446b3cf937eace'}]

for i in range(0, 2):
    threading.Thread(target = book, args = (i,payload[i],), name = 'Thread-' + str(i)).start() 

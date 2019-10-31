#from multiprocessing.process import Process
import time
import redis
import threading
from threading import Thread
import os

def pub(myredis):
    for n in range(10000):
        myredis.publish('channel1', 'blah %d' % n)
        time.sleep(5)


def sub(myredis, name):
    pubsub = myredis.pubsub()
    pubsub.subscribe(['channel2'])
    for item in pubsub.listen():
        print('%s : %s' % (name, item['data']))


if __name__ == '__main__':
    myredis = redis.from_url(os.environ.get("REDIS_URL")) if os.environ.get("REDIS_URL") else redis.Redis()
    Thread(target=pub, args=(myredis,)).start()
    Thread(target=sub, args=(myredis,'reader1')).start()

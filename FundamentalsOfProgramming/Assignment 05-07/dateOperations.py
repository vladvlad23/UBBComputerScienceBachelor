from datetime import *

def getCurrentDate():
    '''
    :return: the current date by calling the datetime.now function from library
    '''
    currentDate = datetime.now()
    return date(currentDate.year,currentDate.month,currentDate.day)


def untilToday(date1):
    '''
    Returns how mnay days have passed from the given date until today
    :param date1: the given date
    :return: the number of days that have passed
    '''
    currentDate = getCurrentDate()
    dateToCompare = date(date1.year,date1.month,date1.day)
    return (dateToCompare-currentDate).days



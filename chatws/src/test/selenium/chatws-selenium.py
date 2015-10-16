import sys
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait

if sys.argv[1]=="firefox":
    browser1 = webdriver.Firefox()
    browser2 = webdriver.Firefox()
else:
    browser1 = webdriver.Chrome()
    browser2 = webdriver.Chrome()

browser1.get("http://localhost:8080/chatws/")
browser2.get("http://localhost:8080/chatws/")

browser1_input_username = browser1.find_element_by_id("username")
browser2_input_username = browser2.find_element_by_id("username")

browser1_input_message = browser1.find_element_by_id("message")
browser2_input_message = browser2.find_element_by_id("message")

browser1_submit = browser1.find_element_by_id("submit")
browser2_submit = browser2.find_element_by_id("submit")

browser1_conversation_area = browser1.find_element_by_id("conversationArea")
browser2_conversation_area = browser2.find_element_by_id("conversationArea")


def test1():
    browser1_input_username.clear()
    browser1_input_username.send_keys("testUser1")
    browser1_input_message.send_keys("111")
    browser1_submit.click()
    
    WebDriverWait(browser1, 3)
    assert "has changed his name to 'testUser1'." in browser1_conversation_area.text
    assert "has changed his name to 'testUser1'." in browser2_conversation_area.text
    assert "testUser1 - 111" in browser1_conversation_area.text
    assert "testUser1 - 111" in browser2_conversation_area.text

def test2():
    browser2_input_username.clear()
    browser2_input_username.send_keys("testUser2")
    browser2_input_message.send_keys("222")
    browser2_submit.click()
    
    WebDriverWait(browser2, 3)
    assert "has changed his name to 'testUser2'." in browser1_conversation_area.text
    assert "has changed his name to 'testUser2'." in browser2_conversation_area.text
    assert "testUser2 - 222" in browser1_conversation_area.text
    assert "testUser2 - 222" in browser2_conversation_area.text

def test3():
    browser2.quit()
    WebDriverWait(browser1, 3)
    assert "User 'testUser2' has disconnected." in browser1_conversation_area.text

test1()
test2()
test3()

browser1.quit()
print("Selenium tests DONE")

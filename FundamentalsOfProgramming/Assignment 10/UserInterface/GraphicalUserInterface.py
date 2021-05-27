from tkinter import *
from tkinter import messagebox
from Repository.Exceptions import GameOverError
from copy import deepcopy
from ArtificialIntelligence import ArtificialIntelligence

class GraphicalUserInterface:

    def __init__(self,gameController):
        self.__gameController = gameController
        self.__computer = ArtificialIntelligence()


    def startGraphicalUserInterface(self):
        '''
        Function will shot the first menu with options to choose between starting the game and reading the rules
        '''
        tk = Tk()
        tk.title("Connect Four Game")

        #this snippet will center the first window as i don't like it being in the corner
        widthScreen = tk.winfo_screenwidth()
        heigthScreen = tk.winfo_screenheight()
        widthWindow = 400
        heigthWindow = 200
        x = (widthScreen/2) - (widthWindow/2)
        y = (heigthScreen/2) - (heigthWindow/2)
        tk.geometry('%dx%d+%d+%d' % (widthWindow, heigthWindow, x, y))

        frame = Frame(tk)
        frame.pack()

        startButton = Button(frame,text = " Start Game ",command = lambda : self.__startGameAndStopWindow(tk))
        readRules = Button(frame,text = " Read Rules ", command = self.__UIReadRules)
        startButtonWithAI = Button(frame,text = "Start Game with AI",command = lambda : self.__startGameWithAIAndStopWindow(tk))
        startButton.grid(row=0)
        startButtonWithAI.grid(row=1)
        readRules.grid(row=2)

        tk.mainloop()

    def __startGameAndStopWindow(self,tk):
        tk.destroy()
        self.__UIStartGame()

    def __startGameWithAIAndStopWindow(self,tk):
        tk.destroy()
        self.__UIStartGameWithAI()

    def __UIMakeMove(self,ovals,canvas,buttonId):
        self.__gameController.makeMove(buttonId)

        board = self.__gameController.getBoard()

        for i in range(6):
            for j in range(7):
                if board[i][j] == '0':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="black")
                elif board[i][j] == 'R':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="red")
                elif board[i][j] == 'Y':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="yellow")

        if self.__gameController.isOver() == True:
            if self.__gameController.getPlayer() == "Y":
                messagebox.showinfo("Game Over", "AND THE WINNER IS: YELLOW PLAYER")
            else:
                messagebox.showinfo("Game Over", "AND THE WINNER IS: RED PLAYER")

    def __UIMakeAIMove(self,ovals,canvas,buttonId):
        try:
            self.__UIMakeMove(ovals,canvas,buttonId)
        except Exception as e:
            messagebox.showerror("Error","Full Column")
            return
        self.__gameController.makeMove(self.__computer.compute(self.__gameController))

        board = self.__gameController.getBoard()

        for i in range(6):
            for j in range(7):
                if board[i][j] == '0':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="black")
                elif board[i][j] == 'R':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="red")
                elif board[i][j] == 'Y':
                    ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="yellow")

        if self.__gameController.isOver() == True:
            if self.__gameController.getPlayer() == "Y":
                messagebox.showinfo("Game Over", "AND THE WINNER IS: YELLOW PLAYER")
            else:
                messagebox.showinfo("Game Over", "AND THE WINNER IS: COMPUTER (and it's a really bad computer so you should feel bad")

    def __UIStartGame(self):
        '''
        Applies the gamecontroller logic with tkinter
        :return:
        '''
        tk = Tk()
        gameFrame = Frame(tk)
        gameFrame.pack()

        canvas = Canvas(tk,width=450,height=500) #width and height of the game window
        ovals = 6*[[]]
        for i in range(6):
            ovals[i] = [None]*7


        for i in range(6):
            for j in range(7):
                ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="black")
        canvas.pack()

        buttons = []
        for i in range(7):
            buttons.append(Button(gameFrame,text=str(i+1),height=5,width=5,
                           command=lambda i=i: self.__UIMakeMove(ovals, canvas, i + 1)))
            buttons[i].grid(row=0,column=i)

        tk.title("ConnectFour game in progress")
        tk.mainloop()

    def __UIStartGameWithAI(self):
        '''
        Applies the gamecontroller logic with tkinter
        '''
        computer = ArtificialIntelligence()
        tk = Tk()
        gameFrame = Frame(tk)
        gameFrame.pack()

        canvas = Canvas(tk,width=450,height=500) #width and height of the game window
        ovals = 6*[[]]
        for i in range(6):
            ovals[i] = [None]*7


        for i in range(6):
            for j in range(7):
                ovals[i][j] = canvas.create_oval(60*j,60*i,60*j+60,60*i+60,fill="black")
        canvas.pack()

        buttons = []
        for i in range(7):
            buttons.append(Button(gameFrame,text=str(i+1),height=5,width=5,
                           command=lambda i=i: self.__UIMakeAIMove(ovals, canvas, i + 1)))
            buttons[i].grid(row=0,column=i)

        tk.title("ConnectFour game in progress")
        tk.mainloop()

    def __UIReadRules(self):
        object = "\t Object: \n" +\
                 "To win Connect Four you must be the first player to get four of your colored checkers in a row \n either horizontally, vertically or diagonally.\n"

        messagebox.showinfo("Rules",object)



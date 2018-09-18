import os
import sys
import compute as cp

max_grades = []
students = []

# read class.txt
def read_students(file_name):
    with open(file_name) as files:
        for line in files:
            line = line.strip("\n")
            students.append(line.split("|"))

# read all files
def read_marks(file_name):
    found = False
    inputs = False
    for i in range(len(students)):
        found = False
        with open(file_name) as files:
            max_mark = files.readline()
            if not inputs:
                max_grades.append(max_mark.strip("\n"))
                inputs = True

            for line in files:
                line = line.strip("\n")
                grades = line.split("|")
                if students[i][0] == grades[0]:
                    students[i].append(grades[1])
                    found = True
        if not found:
            students[i].append("0")

# display main menu
def menu():
    choice = input("""
-------------------Main Menu----------------------
1> Display Individual Component
2> Display component Average
3> Display Standard Report
4> Sort by alternate column
5> change Pass/Fail Point
6> Exit
-------------------------------------------------- 
Enter Choice: """)

    if choice == '1':
        cp.individual(students, max_grades)
        menu()

    elif choice == '2':
        choice3 = input("-> Select Component for Average(A1,A2,PR,T1,T2):")
        if choice3 == 'a1' or choice3 == 'A1':
            cp.average('a1')
        elif choice3 == 'a2' or choice3 == 'A2':
            cp.average('a2')
        elif choice3 == 'pr' or choice3 == 'PR':
            cp.average('project')
        elif choice3 == 't1' or choice3 == 'T1':
            cp.average('test1')
        elif choice3 == 't2' or choice3 == 'T2':
            cp.average('test2')
        else:
            print("incorrect choice")
        menu()

    elif choice == '3':
        print("-> Standard Report:")
        cp.report(students, max_grades, 1)
        menu()

    elif choice == '4':
        print("-> Standard Report:")
        cp.report(students, max_grades, 0)
        menu()

    elif choice == '5':
        point = input("-> Enter Fail Point (between 0 to 100):")
        cp.report(students, max_grades, point)
        menu()

    elif choice == '6':
        print("Good Bye")
        sys.exit()

    else:
        print("Incorrect Choice. Choose Between 1 to 6")
        menu()

#main
if __name__ == "__main__":

    read_students("class.txt")
    read_marks("a1.txt")
    read_marks("a2.txt")
    read_marks("project.txt")
    read_marks("test1.txt")
    read_marks("test2.txt")
    menu()


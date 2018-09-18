import os
import sys

#a1_w = 7.5
#a2_w = 7.5
#pr_w = 25
#test1_w = 30
#test2_w = 30


# for component Average
def average(choice3):

    with open(choice3 + ".txt") as fh:
        sum = 0
        numlines = 0
        line2 = fh.readline()
        for line in fh:
            n = line.split('|')[-1]
            sum += float(n)
            numlines += 1
        average = sum / numlines
        avg = ("{0:.2f}".format(average))

    print(" ")
    print(choice3.upper()+" Average:" + avg + "/" + line2)

# display individual component
def individual(students, max_grades):
    choice2 = input("-> Select Component(A1,A2,PR,T1,T2):")
    index = -1
    if choice2 == 'a1' or choice2 == 'A1':
        index = 3
    elif choice2 == 'a2' or choice2 == 'A2':
        index = 4
    elif choice2 == 'pr' or choice2 == 'PR':
        index = 5
    elif choice2 == 't1' or choice2 == 'T1':
        index = 6
    elif choice2 == 't2' or choice2 == 'T2':
        index = 7
    else:
        print("Incorrect value")

    if index >= 3 and index <= 7:
        print(choice2.upper() + " grades: (" + max_grades[index - 3] + ")")
        for j in students:
            print(j[0] + "\t" + j[1] + "," + j[2] + "\t" + j[index])


# for computing total grade
def report(students, max_grades, var):

    for i in students:
        a1 = float(i[3]) * 7.5 / float(max_grades[0])
        a2 = float(i[4]) * 7.5 / float(max_grades[1])
        pr = float(i[5]) * 25 / float(max_grades[2])
        t1 = float(i[6]) * 30 / float(max_grades[3])
        t2 = float(i[7]) * 30 / float(max_grades[4])

        total_grades = int(a1 + a2 + pr + t1 + t2)
        i.append(total_grades)

        if var == 1 or var == 0:
            fl_grade = grade_info(total_grades)
            try:
                i[9] = fl_grade
            except:
                i.append(fl_grade)
        else:
            f2_grade = changepoint(total_grades, var)
            try:
                i[9] = f2_grade
            except:
                i.append(f2_grade)

    if var == 1:
       students.sort()
       displayreport(students)
    elif var == 0:
        displayreport(students)
        sort(students)
    else:
        displayreport(students)


# for computing letter grade
def grade_info(total_grades):
    if total_grades >= 94.0 and total_grades <= 100:
        fl_grade = 'A+'
    elif total_grades >= 87.0 and total_grades <= 93.0:
        fl_grade = 'A'
    elif total_grades >= 80.0 and total_grades <= 86.0:
        fl_grade = 'A-'
    elif total_grades >= 73.0 and total_grades <= 79.0:
        fl_grade = "B+"
    elif total_grades >= 66.0 and total_grades <= 72.0:
        fl_grade = 'B'
    elif total_grades >= 58.0 and total_grades <= 65.0:
        fl_grade = 'B-'
    elif total_grades >= 51.0 and total_grades <= 57.0:
        fl_grade = 'C'
    else:
        fl_grade = 'F'

    return fl_grade

# for creating space in report
def createspace(str, size):
    space = " "
    for p in range(size):
        if p > len(str) - 1:
            space += " "
        else:
            space += str[p]

    return space

# for display report
def displayreport(students):

    print(" ")
    print(createspace("ID", 6) +
          createspace("LN", 7) +
          createspace("FN", 7) +
          createspace("A1", 4) +
          createspace("A2", 4) +
          createspace("PR", 4) +
          createspace("T1", 4) +
          createspace("T2", 4) +
          createspace("GR", 4) +
          createspace("FL", 4))

    for i in students:
        print(createspace(i[0], 6) +
              createspace(i[2], 7) +
              createspace(i[1], 7) +
              createspace((i[3], " ")[i[3] == '0'], 4) +
              createspace((i[4], " ")[i[4] == '0'], 4) +
              createspace((i[5], " ")[i[5] == '0'], 4) +
              createspace((i[6], " ")[i[6] == '0'], 4) +
              createspace((i[7], " ")[i[7] == '0'], 4) +
              createspace(repr(i[8]), 4) +
              createspace(i[9], 4))
    print(" ")

# For sorting list
def sort(students):
    sort_choice = input("-> Sort-by LT(last name) or GR(grade):")

    if sort_choice == 'LT' or sort_choice == 'lt' or sort_choice == 'last name':
        students.sort(key=lambda x: x[2])
        displayreport(students)
    elif sort_choice == 'GR' or sort_choice == 'gr' or sort_choice == 'grade':
        students.sort(key=lambda x: x[8])
        displayreport(students)
    else:
        print("Incorrect Choice: sort by LT or GR")

# for change pass/fail point
def changepoint(total_grades, var):
    point = int(var)
    point2 = 100 - point
    point3 = point2/7

    if total_grades <= point:
        f2_grade = 'F'
    elif total_grades >= point+1 and total_grades <= point + point3:
        f2_grade = 'C'
    elif total_grades >= point + point3+1 and total_grades <= point + (2* point3):
        f2_grade = 'B-'
    elif total_grades >= point + (2* point3) + 1 and total_grades <= point + (3 * point3):
        f2_grade = 'B'
    elif total_grades >= point + (3 * point3) + 1 and total_grades <= point + (4 * point3):
        f2_grade = 'B+'
    elif total_grades >= point + (4 * point3) + 1 and total_grades <= point + (5 * point3):
        f2_grade = 'A-'
    elif total_grades >= point + (5 * point3) + 1 and total_grades <= point + (6 * point3):
        f2_grade = 'A'
    else:
        f2_grade = 'A+'
    return f2_grade
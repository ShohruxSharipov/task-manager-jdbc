import db.TaskDAO;
import db.UserDAO;
import model.Task;
import model.User;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        User user;
        UserDAO userDAO = new UserDAO();
        while (true) {
            System.out.print("Login or Create Account ? L/C : ");
            String lc = sc.nextLine();
            if (Objects.equals(lc, "L") || Objects.equals(lc, "l")) {
                System.out.print("Login : ");
                String login = sc.nextLine();
                System.out.print("Password : ");
                String password = sc.nextLine();
                int uID = userDAO.authenticate(login, PasswordUtil.hash(password));
                if (uID != 0) {
                    user = userDAO.getUser(uID);
                    console(user);
                } else {
                    System.out.println("Invalid username or password !");

                }
            } else if (Objects.equals(lc, "C") || Objects.equals(lc, "c")) {
                System.out.print("Full Name : ");
                String name = sc.nextLine();
                System.out.print("Username : ");
                String username = sc.nextLine();
                System.out.print("Password : ");
                String password = sc.nextLine();
                User newUser = new User(0, name, username, PasswordUtil.hash(password));
                if (userDAO.addUser(newUser)) {
                    System.out.println("Successfully added user !");
                }
            } else if (Objects.equals(lc.toLowerCase(Locale.ROOT), "exit")) {
                System.exit(0);
            } else {
                System.out.println("Command not found !");
            }

        }

    }


    public static void console(User user) {
        TaskDAO taskDAO = new TaskDAO();
        while (true) {
            System.out.print(user.name + "$ : ");
            String answer = sc.nextLine();
            answer = answer.toLowerCase().trim();
            if (answer.equals("ls")) {
                List<Task> tasks = taskDAO.getTasks(user.id);
                printList(tasks);
            } else if (answer.equals("ls done")) {
                List<Task> tasks = taskDAO.getTasks(user.id, true);
                printList(tasks);
            } else if (answer.equals("ls todo")) {
                List<Task> tasks = taskDAO.getTasks(user.id, false);
                printList(tasks);
            } else if (answer.equals("add")) {
                System.out.print("Name :");
                String name = sc.nextLine();
                System.out.print("Description :");
                String description = sc.nextLine();
                Task task = new Task(0, name, description, user.id, false);
                taskDAO.addTask(task);
            } else if (answer.startsWith("rm")) {
                String[] parts = answer.split(" ");
                if (parts.length == 2) {
                    if (!parts[0].equals("rm")) continue;
                    try {
                        int id = Integer.parseInt(parts[1]);
                        if (taskDAO.markDone(id,user.id)) {
                            System.out.println("Task successfully marked as done!");
                        } else {
                            System.out.println("Task not found!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task id.");
                    }
                }
            } else if (answer.equals("logout")) {
                break;
            } else {
                System.out.println("Command not found");
            }
        }
    }

    private static String fit(String text, int max) {
        if (text == null) return "";
        return text.length() <= max
                ? text
                : text.substring(0, max - 3) + "...";
    }

    private static void printList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found !");
            return;
        }
        System.out.println("+------+----------------------+------------------------------------------+-------+");
        System.out.println("| ID   | NAME                 | DESCRIPTION                              | DONE  |");
        System.out.println("+------+----------------------+------------------------------------------+-------+");

        for (Task task : tasks) {
            System.out.printf(
                    "| %-4d | %-20s | %-40s | %-5s |%n",
                    task.id,
                    fit(task.name, 20),
                    fit(task.description, 40),
                    task.done
            );
        }

        System.out.println("+------+----------------------+------------------------------------------+-------+");
    }

}

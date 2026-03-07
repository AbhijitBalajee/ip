# Abs - Task Manager Chatbot

Abs is a personal task management chatbot that runs in your terminal.
It helps you track todos, deadlines, and events, and saves your tasks automatically.

When started, Abs greets you with:

```
    -------------------------------------------
      █████╗ ██████╗ ███████╗
     ██╔══██╗██╔══██╗██╔════╝
     ███████║██████╔╝███████╗
     ██╔══██║██╔══██╗╚════██║
     ██║  ██║██████╔╝███████║
     ╚═╝  ╚═╝╚═════╝ ╚══════╝

    Hellooo! I'm Abs!
    What is your name?
    -------------------------------------------
```

## Features

- **Add** todos, deadlines, and events
- **Mark** and unmark tasks as done
- **Delete** tasks
- **Find** tasks by keyword
- **View** tasks on a specific date
- Auto-saves all tasks to disk

## Setting Up

1. Ensure you have Java 11 or later installed
2. Download the latest `abs.jar` from the releases page
3. Run the following command in your terminal:

```
java -jar abs.jar
```

## Usage

When you start Abs, it will ask for your name and greet you personally.
Type any of the commands below to manage your tasks.

[!TIP]
All commands are case-insensitive. For example, BYE, Bye, and bye all work the same way.

### Adding Tasks

**Todo** — a task with no time constraint
```
todo <description>
```
Example: `todo read book`

Expected output:
```
    -------------------------------------------
    Got it, [name]! I've added this task:
      [T][ ] read book
    Now you have 1 tasks in the list.
    -------------------------------------------
```

**Deadline** — a task with a due date
```
deadline <description> /by <date>
```
Example: `deadline submit report /by 2026-12-01`

Expected output:
```
    -------------------------------------------
    Got it, [name]! I've added this task:
      [D][ ] submit report (by: Dec 01 2026)
    Now you have 2 tasks in the list.
    -------------------------------------------
```

**Event** — a task with a start and end time
```
event <description> /from <start> /to <end>
```
Example: `event project meeting /from 2026-12-01 0900 /to 2026-12-01 1100`

Expected output:
```
    -------------------------------------------
    Got it, [name]! I've added this task:
      [E][ ] project meeting (from: Dec 01 2026, 9:00AM to: Dec 01 2026, 11:00AM)
    Now you have 3 tasks in the list.
    -------------------------------------------
```

> [!NOTE]
> Dates should be in `yyyy-MM-dd` format for deadlines and `yyyy-MM-dd HHmm` for events.
> Natural language like `next friday` is also accepted but won't support date search.

### Viewing Tasks

**List all tasks**
```
list
```
Expected output:
```
    -------------------------------------------
    Here are the tasks in your list, [name]:
    1.[T][ ] read book
    2.[D][ ] submit report (by: Dec 01 2026)
    3.[E][ ] project meeting (from: Dec 01 2026, 9:00AM to: Dec 01 2026, 11:00AM)
    -------------------------------------------
```

**Find tasks by keyword**
```
find <keyword>
```
Example: `find book`

Expected output:
```
    -------------------------------------------
    Here are the matching tasks I found for you, [name]:
    1.[T][ ] read book
    -------------------------------------------
```

**View tasks on a specific date**
```
date <date>
```
Example: `date 2026-12-01`

Expected output:
```
    -------------------------------------------
    Okay [name], here are the tasks on Dec 01 2026:
    1.[D][ ] submit report (by: Dec 01 2026)
    2.[E][ ] project meeting (from: Dec 01 2026, 9:00AM to: Dec 01 2026, 11:00AM)
    -------------------------------------------
```

### Managing Tasks

**Mark a task as done**
```
mark <task number>
```
Example: `mark 1`

Expected output:
```
    -------------------------------------------
    Nice job, [name]! I've marked this task as done:
      [T][X] read book
    -------------------------------------------
```

**Unmark a task**
```
unmark <task number>
```
Example: `unmark 1`

Expected output:
```
    -------------------------------------------
    OK [name], I've marked this task as not done yet:
      [T][ ] read book
    -------------------------------------------
```

**Delete a task**
```
delete <task number>
```
Example: `delete 1`

Expected output:
```
    -------------------------------------------
    Noted, [name]. I've removed this task:
      [T][ ] read book
    Now you have 2 tasks remaining.
    -------------------------------------------
```

### Exiting
```
bye
```
Expected output:
```
    -------------------------------------------
    Bye [name]!
    Hope to see you again realll soonnn!
    -------------------------------------------
```

## Command Summary

| Command | Format | Example |
|---|---|---|
| Todo | `todo <desc>` | `todo read book` |
| Deadline | `deadline <desc> /by <date>` | `deadline report /by 2026-12-01` |
| Event | `event <desc> /from <start> /to <end>` | `event meeting /from 2026-12-01 0900 /to 2026-12-01 1100` |
| List | `list` | `list` |
| Mark | `mark <number>` | `mark 1` |
| Unmark | `unmark <number>` | `unmark 1` |
| Delete | `delete <number>` | `delete 1` |
| Find | `find <keyword>` | `find book` |
| Date | `date <date>` | `date 2026-12-01` |
| Bye | `bye` | `bye` |

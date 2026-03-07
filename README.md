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

### Adding Tasks

**Todo** — a task with no time constraint
```
todo <description>
```
Example: `todo read book`

**Deadline** — a task with a due date
```
deadline <description> /by <date>
```
Example: `deadline submit report /by 2026-12-01`

**Event** — a task with a start and end time
```
event <description> /from <start> /to <end>
```
Example: `event project meeting /from 2026-12-01 0900 /to 2026-12-01 1100`

> [!NOTE]
> Dates should be in `yyyy-MM-dd` format for deadlines and `yyyy-MM-dd HHmm` for events.
> Natural language like `next friday` is also accepted but won't support date search.

### Viewing Tasks

**List all tasks**
```
list
```

**Find tasks by keyword**
```
find <keyword>
```
Example: `find book`

**View tasks on a specific date**
```
date <date>
```
Example: `date 2026-12-01`

### Managing Tasks

**Mark a task as done**
```
mark <task number>
```
Example: `mark 1`

**Unmark a task**
```
unmark <task number>
```
Example: `unmark 1`

**Delete a task**
```
delete <task number>
```
Example: `delete 1`

### Exiting
```
bye
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

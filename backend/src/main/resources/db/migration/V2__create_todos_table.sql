CREATE TABLE IF NOT EXISTS todos (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    status TEXT NOT NULL DEFAULT 'PENDING',
    due_date TIMESTAMPTZ,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_status CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'))
);

CREATE INDEX idx_todos_user_id ON todos(user_id);

CREATE TABLE IF NOT EXISTS todo_categories (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    description TEXT
);

CREATE TABLE IF NOT EXISTS todo_category_rlt (
    todo_id BIGSERIAL NOT NULL,
    category_id BIGSERIAL NOT NULL,
    PRIMARY KEY (todo_id, category_id),
    CONSTRAINT fk_todo FOREIGN KEY (todo_id) REFERENCES todos(id) ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES todo_categories(id) ON DELETE CASCADE
);

CREATE INDEX idx_todo_category_rlt_todo_id ON todo_category_rlt(todo_id);
CREATE INDEX idx_todo_category_rlt_category_id ON todo_category_rlt(category_id);

CREATE TABLE IF NOT EXISTS todo_notifs (
    id BIGSERIAL PRIMARY KEY,
    todo_id BIGSERIAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'PENDING',
    payload TEXT,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_todo FOREIGN KEY (todo_id) REFERENCES todos(id) ON DELETE CASCADE,
    CONSTRAINT chk_notif_status CHECK (status IN ('PENDING', 'SENT', 'FAILED'))
);

CREATE INDEX idx_todo_notifs_todo_id ON todo_notifs(todo_id);


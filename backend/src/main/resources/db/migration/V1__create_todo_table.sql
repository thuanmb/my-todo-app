CREATE TABLE IF NOT EXISTS todo (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(512) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
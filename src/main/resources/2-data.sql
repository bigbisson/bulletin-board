-- Sample data for testing (optional)
-- Uncomment to use sample data:

INSERT INTO posts (title, author, password, content, view_count, created_at, is_deleted)
VALUES
    ('Welcome to Bulletin Board', 'Admin', 'admin123', 'This is the first post!', 10, CURRENT_TIMESTAMP, FALSE),
    ('Test Post', 'User1', 'password', 'This is a test post.', 5, CURRENT_TIMESTAMP, FALSE);

-- Drop table if exists for clean setup
DROP TABLE IF EXISTS posts CASCADE;

-- Create posts table
CREATE TABLE posts (
    id              SERIAL PRIMARY KEY,
    title           VARCHAR(100) NOT NULL,
    author          VARCHAR(20) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    content         TEXT,
    view_count      INT DEFAULT 0,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NULL,
    is_deleted      BOOLEAN DEFAULT FALSE
);

-- Create indexes for better performance
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX idx_posts_is_deleted ON posts(is_deleted);

-- Add table comments
COMMENT ON TABLE posts IS 'Bulletin board posts table';
COMMENT ON COLUMN posts.password IS 'Plain text password for edit/delete verification';
COMMENT ON COLUMN posts.is_deleted IS 'Soft delete flag - true means deleted';

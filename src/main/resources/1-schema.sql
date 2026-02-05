-- Idempotent schema: safe to run multiple times
-- Skips table creation if it already exists (preserves data)

-- Create posts table if not exists
CREATE TABLE IF NOT EXISTS posts (
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

-- Create indexes if not exist
CREATE INDEX IF NOT EXISTS idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_posts_is_deleted ON posts(is_deleted);

-- Add table comments (will error if table doesn't exist, but that's OK)
COMMENT ON TABLE posts IS 'Bulletin board posts table';
COMMENT ON COLUMN posts.password IS 'Plain text password for edit/delete verification';
COMMENT ON COLUMN posts.is_deleted IS 'Soft delete flag - true means deleted';

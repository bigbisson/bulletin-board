/**
 * Bulletin Board - Client-side Validation
 * 
 * Korean characters: 2 bytes each
 * English/ASCII characters: 1 byte each
 */

// Calculate UTF-8 byte length
function getByteLength(str) {
    let byteLength = 0;
    for (let i = 0; i < str.length; i++) {
        const charCode = str.charCodeAt(i);
        if (charCode <= 0x007F) {
            byteLength += 1;  // 1 byte for ASCII
        } else if (charCode <= 0x07FF) {
            byteLength += 2;  // 2 bytes for most common characters (Korean, etc.)
        } else {
            byteLength += 3;  // 3 bytes for rare characters
        }
    }
    return byteLength;
}

// Validate individual field
function validateField(field, maxBytes) {
    const byteLength = getByteLength(field.value);
    if (byteLength > maxBytes) {
        field.classList.add('is-invalid');
        return false;
    } else {
        field.classList.remove('is-invalid');
        return true;
    }
}

// Full form validation
function validateForm() {
    const author = document.getElementById('author');
    const title = document.getElementById('title');
    const content = document.getElementById('content');
    const password = document.getElementById('password');
    
    let isValid = true;
    
    // Validate author (max 20 bytes = 10 Korean chars)
    if (!validateField(author, 20)) {
        alert('Author name is too long! Maximum: 10 Korean or 20 English characters.');
        author.focus();
        isValid = false;
    }
    
    // Validate title (max 100 bytes = 50 Korean chars or 100 English chars)
    if (!validateField(title, 100)) {
        alert('Title is too long! Maximum: 50 Korean or 100 English characters.');
        title.focus();
        isValid = false;
    }
    
    // Validate content
    if (!content.value.trim()) {
        alert('Content is required!');
        content.focus();
        isValid = false;
    }
    
    // Validate password (for new posts)
    if (password && password.value.length < 4) {
        alert('Password must be at least 4 characters long!');
        password.focus();
        isValid = false;
    }
    
    return isValid;
}
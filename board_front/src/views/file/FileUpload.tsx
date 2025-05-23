import { uploadPost } from '@/apis/uploadApi';
import React, { ChangeEvent, FormEvent, useState } from 'react'

// PostForm
function FileUpload() {
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [file, setFile] = useState<File | null>(null);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) setFile(e.target.files[0]);
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const formData = new FormData();

    formData.append('data', new Blob([
      JSON.stringify({ title, content })
    ], { type: 'application/json'}));

    if (file) formData.append('file', file);
    await uploadPost(formData);
    alert('게시글 등록 완료');
  }

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input 
          type="text" 
          value={title}
          placeholder='제목'
          onChange={e => setTitle(e.target.value)}  
        />
        <br />
        <textarea 
          value={content}
          placeholder='내용'
          onChange={e => setContent(e.target.value)}
        ></textarea>
        <br />
        <input type="file" onChange={handleFileChange} />
        <button type='submit'>등록</button>
      </form>
    </div>
  )
}

export default FileUpload
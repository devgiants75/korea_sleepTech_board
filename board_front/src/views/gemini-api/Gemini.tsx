import { askGemini } from '@/apis/gemini/gemini';
import React, { useState } from 'react';

function Gemini() {
  const [input, setInput] = useState('');
  const [chat, setChat] = useState<string[]>([]);

  const handleSend = async () => {
    if (!input.trim()) return;
    setChat(prev => [...prev, `You: ${input}`]);
    const answer = await askGemini(input, "");
    setChat(prev => [...prev, `Gemini: ${answer}`]);
    setInput('');
  };

  return (
    <div style={{ width: 400, margin: '0 auto' }}>
      <div style={{ height: 300, overflow: 'auto', border: '1px solid #ddd', padding: 10 }}>
        {chat.map((msg, idx) => <div key={idx}>{msg}</div>)}
      </div>
      <input
        value={input}
        onChange={e => setInput(e.target.value)}
        onKeyDown={e => e.key === 'Enter' && handleSend()}
        style={{ width: '100%', padding: 8 }}
      />
      <button onClick={handleSend} style={{ marginTop: 8 }}>Send</button>
    </div>
  );
};

export default Gemini
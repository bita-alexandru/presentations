**using Python 3.14*

1. `python -m venv .venv`

2. `.venv\Scripts\activate`

3. `python -m pip install -r requirements.txt`

4. `adk create <your_new_agent's_name>` or just fill in `eduardo\.env` as instructed by `eduardo\.env.example`

5. 
    * `adk web` while cwd is set to the root of this project (`.\google-adk-demo`)
    * `Ctrl-C` to terminate and `cd .\eduardo\resources\<another-agent>` to `adk web` other demos, where `<another-agent>` can be either `"4-multi-agent"`, `"5-loop-agent"` or `"6-parallel-agent"`
    * inspect `agent.py` to see what an agent is supposed to do and check out `.\eduardo\resources\*.txt` for additional stuff that you can copy-paste or add into `agent.py`

If you already have a Google account, you can get your API key from here https://aistudio.google.com/u/0/apikey for free <i><sup><sub>(it only costs your data privacy which Google already has so it's fine)</sub></sup></i>
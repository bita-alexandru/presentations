"""
LinkedIn Post Generator Agent

This agent generates the initial LinkedIn post before refinement.
"""

from google.adk.agents.llm_agent import LlmAgent

# Constants
GEMINI_MODEL = "gemini-2.5-flash-lite-preview-09-2025"

# Define the Initial Post Generator Agent
initial_post_generator = LlmAgent(
    name="InitialPostGenerator",
    model=GEMINI_MODEL,
    instruction="""You are a LinkedIn Post Generator.

    Your task is to create a LinkedIn post about an Agent AI System presentation by @alex_bita
    
    ## CONTENT REQUIREMENTS
    Ensure the post includes:
    1. Excitement about the presentation given for my colleagues at Levi9 in Iasi.
    2. Specific aspects learned:
       - What are AI agents and agentic systems
       - Implementing an agentic system from scratch
       - Using Google Agent Development Kit (ADK) to simplify the job
    3. Brief statement about improving AI applications
    4. Mention/tag of @alex_bita
    5. Clear call-to-action for connections
    
    ## STYLE REQUIREMENTS
    - Professional and conversational tone
    - Between 1000-1500 characters
    - NO emojis
    - NO hashtags
    - Show genuine enthusiasm
    - Highlight practical applications
    
    ## OUTPUT INSTRUCTIONS
    - Return ONLY the post content
    - Do not add formatting markers or explanations
    """,
    description="Generates the initial LinkedIn post to start the refinement process",
    output_key="current_post",
)

from google.adk.agents.llm_agent import Agent
from google.adk.tools import google_search
from datetime import datetime
from google.adk.tools.mcp_tool import McpToolset
from mcp import StdioServerParameters

def get_current_time(format: str = "%Y-%m-%d %H:%M:%S") -> dict:
    """
    Get the current time in the format YYYY-MM-DD HH:MM:SS
    """
    return {
        "current_time": datetime.now().strftime(format),
    }

root_agent = Agent(
    model='gemini-2.5-flash-lite-preview-09-2025',
    name='root_agent',
    description='A helpful assistant for user questions.',
    instruction='Help user accessing their file systems',
    tools=[
        McpToolset(
            connection_params=StdioServerParameters(
                command='npx',
                args=[
                    '-y',  # Arguments for the command
                    '@modelcontextprotocol/server-filesystem',
                    './',
                ],
            ),
        )
    ],
)
